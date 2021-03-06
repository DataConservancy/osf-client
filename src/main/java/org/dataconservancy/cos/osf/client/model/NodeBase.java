/*
 * Copyright 2016 Johns Hopkins University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dataconservancy.cos.osf.client.model;

import static org.dataconservancy.cos.osf.client.support.JodaSupport.DATE_TIME_FORMATTER;

import java.util.List;
import java.util.Map;

import org.dataconservancy.cos.osf.client.support.DateTimeTransform;
import org.dataconservancy.cos.osf.client.support.JodaSupport;
import org.dataconservancy.cos.osf.client.support.LicenseHashUriGenerator;
import org.dataconservancy.cos.osf.client.support.ToBooleanTransform;
import org.dataconservancy.cos.osf.client.support.TruncatingTransform;
import org.dataconservancy.cos.osf.client.support.UrlToIdTransform;
import org.dataconservancy.cos.rdf.annotations.AnonIndividual;
import org.dataconservancy.cos.rdf.annotations.IndividualUri;
import org.dataconservancy.cos.rdf.annotations.OwlIndividual;
import org.dataconservancy.cos.rdf.annotations.OwlProperty;
import org.dataconservancy.cos.rdf.support.OwlClasses;
import org.dataconservancy.cos.rdf.support.OwlProperties;
import org.dataconservancy.cos.rdf.support.ToStringTransform;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.RelType;
import com.github.jasminb.jsonapi.ResolutionStrategy;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Links;
import com.github.jasminb.jsonapi.annotations.Relationship;

/**
 * Contains methods common to both Nodes (which are either Projects or Components)
 * and Registrations (which are snapshots of Projects or Components).
 * @author khanson
 *
 */
public abstract class NodeBase {

	/**node id*/
	@Id
	@OwlProperty(OwlProperties.OSF_HAS_ID)
	@IndividualUri
	private String id;

	/**List of users who are contributors to this node. */
	@Relationship(value = "contributors", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
	@OwlProperty(OwlProperties.OSF_HAS_CONTRIBUTOR)
	private List<Contributor> contributors;

	/**List of top-level folders (actually cloud-storage providers) associated with this node.
	 * This is the starting point for accessing the actual files stored within this node.*/
	@Relationship(value = "files", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
	@OwlProperty(OwlProperties.OSF_HAS_HASPROVIDER)
	private List<File> files;

	/**Root node if you walk up the tree of projects/components.*/
	@Relationship(value = "root", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	@OwlProperty(value = OwlProperties.OSF_HAS_ROOT, transform = UrlToIdTransform.class)
	private String root;

	/**If this node is a child node of another node, the parent's canonical endpoint will 
	 * be available in the /parent/links/related/href key. Otherwise, it will be null.*/
	@Relationship(value = "parent", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	@OwlProperty(value = OwlProperties.OSF_HAS_PARENT, transform = UrlToIdTransform.class)
	private String parent;

	/**Link to list of registrations related to the current node*/
	@Relationship(value = "registrations", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	// TODO: @OwlProperty(....)
	private String registrations;

	/**If this node was forked from another node, the canonical endpoint of the node that was 
	 * forked from will be available in the /forked_from/links/related/href key. Otherwise, it will be null.*/
	@Relationship(value = "forked_from", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	@OwlProperty(value = OwlProperties.OSF_FORKED_FROM, transform = UrlToIdTransform.class)
	private String forked_from;

	/**Link to list of links to other Nodes.*/
	@Relationship(value = "node_links", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	private String node_links;

	/**Link to list of logs for Node.*/
	@Relationship(value = "logs", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
	// TODO: Logs class and annotation
	private String logs;

	@Relationship(value = "license", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
	@OwlProperty(OwlProperties.OSF_HAS_LICENSE)
	private License license;

	@Relationship(value = "wikis", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
	@OwlProperty(OwlProperties.OSF_HAS_WIKI)
	private List<Wiki> wikis;

	/**Gets other links found in data.links:{} section of JSON**/
	@Links
	Map<String, ?> links;

	/**pagination links, applies when list is returned**/
	private org.dataconservancy.cos.osf.client.model.Links pageLinks;

	/**Node category, must be one of the allowed values.*/
	@OwlProperty(value = OwlProperties.OSF_HAS_CATEGORY, transform = ToStringTransform.class)
	private Category category;

	/**description of the node*/
	@OwlProperty(OwlProperties.OSF_HAS_DESCRIPTION)
	private String description;

	/**title of project or component*/
	@OwlProperty(OwlProperties.OSF_HAS_TITLE)
	private String title;

	/**list of tags that describe the node*/
	@OwlProperty(OwlProperties.OSF_HAS_TAG)
	private List<String> tags;
	
	/**List of strings representing the permissions for the current user on this node*/
	private List<Permission> current_user_permissions;
	
	/**timestamp that the node was created*/
	@OwlProperty(value = OwlProperties.OSF_HAS_DATECREATED, transform = DateTimeTransform.class)
	private DateTime date_created;
	
	/**timestamp when the node was last updated*/
	@OwlProperty(value = OwlProperties.OSF_HAS_DATEMODIFIED, transform = DateTimeTransform.class)
	private DateTime date_modified;
	
	/**is this node a fork of another node?*/
	@OwlProperty(OwlProperties.OSF_IS_FORK)
	private Boolean isFork;
	
	/**as this node been made publicly-visible?*/
	@OwlProperty(OwlProperties.OSF_IS_PUBLIC)
	private Boolean isPublic;
	
	/**has this project been registered?*/
	@OwlProperty(OwlProperties.OSF_IS_REGISTRATION)
	private Boolean isRegistration;
	
	/**is project a collection?*/
	@OwlProperty(OwlProperties.OSF_IS_COLLECTION)
	private Boolean isCollection;

	
    /**Link to primary institution for node.
     * TODO: doesn't appear to work yet - commenting out to prevent error*/
    //@Relationship(value = "primary_institution", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
    //private Institution primary_institution;     

	/*public Institution getPrimary_institution() {
		return primary_institution;
	}

	public void setPrimary_institution(Institution primary_institution) {
		this.primary_institution = primary_institution;
	}*/
		
	
	public NodeBase() {
		super();
	}

	public Category getCategory() {
	    return category;
	}

	public void setCategory(Category category) {
	    this.category = category;
	}

	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public String getTitle() {
	    return title;
	}

	public void setTitle(String title) {
	    this.title = title;
	}

	public List<String> getTags() {
	    return tags;
	}

	public void setTags(List<String> tags) {
	    this.tags = tags;
	}

	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}

	public List<Permission> getCurrent_user_permissions() {
	    return current_user_permissions;
	}

	public void setCurrent_user_permissions(List<Permission> current_user_permissions) {
	    this.current_user_permissions = current_user_permissions;
	}

	public String getDate_created() {
		if (this.date_created!=null) {
			return this.date_created.toString(DATE_TIME_FORMATTER);
		} else {
			return null;
		}
	}

	public void setDate_created(String date_created) {
		if (date_created!=null){
			this.date_created = JodaSupport.parseDateTime(date_created);
		} else {
			this.date_created = null;
		}
	}

	public String getDate_modified() {
		if (this.date_modified!=null) {
			return this.date_modified.toString(DATE_TIME_FORMATTER);
		} else {
			return null;
		}
	}

	public void setDate_modified(String date_modified) {
		if (date_modified!=null){
			this.date_modified = JodaSupport.parseDateTime(date_modified);
		} else {
			date_modified=null;
		}
	}

	public Boolean isFork() {
	    return isFork;
	}

	public void setFork(Boolean fork) {
	    isFork = fork;
	}

	public Boolean isPublic() {
	    return isPublic;
	}

	public void setPublic(Boolean aPublic) {
	    isPublic = aPublic;
	}

	public Boolean isRegistration() {
	    return isRegistration;
	}

	public void setRegistration(Boolean registration) {
	    isRegistration = registration;
	}

	public Boolean isCollection() {
	    return isCollection;
	}

	public void setCollection(Boolean collection) {
	    isCollection = collection;
	}

	public List<File> getFiles() {
	    return files;
	}

	public void setFiles(List<File> files) {
	    this.files = files;
	}

	public List<Contributor> getContributors() {
		return contributors;
	}

	public void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getForked_from() {
		return forked_from;
	}

	public void setForked_from(String forked_from) {
		this.forked_from = forked_from;
	}

	public String getRegistrations() {
		return registrations;
	}

	public void setRegistrations(String registrations) {
		this.registrations = registrations;
	}

	public String getNode_links() {
		return node_links;
	}

	public void setNode_links(String node_links) {
		this.node_links = node_links;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public Map<String, ?> getLinks() {
		return links;
	}

	public void setLinks(Map<String, ?> links) {
		this.links = links;
	}

	public org.dataconservancy.cos.osf.client.model.Links getPageLinks() {
	    return pageLinks;
	}

	@JsonProperty("links")
	public void setPageLinks(org.dataconservancy.cos.osf.client.model.Links pageLinks) {
	    this.pageLinks = pageLinks;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public List<Wiki> getWikis() {
		return wikis;
	}

	public void setWikis(List<Wiki> wikis) {
		this.wikis = wikis;
	}
}
