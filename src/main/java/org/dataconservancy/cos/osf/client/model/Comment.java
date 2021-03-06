/*
 * Copyright 2016 Johns Hopkins University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dataconservancy.cos.osf.client.model;

import static org.dataconservancy.cos.osf.client.support.JodaSupport.DATE_TIME_FORMATTER;

import java.net.URI;
import java.util.Map;

import com.github.jasminb.jsonapi.RelType;
import com.github.jasminb.jsonapi.ResolutionStrategy;
import com.github.jasminb.jsonapi.annotations.Relationship;
import org.dataconservancy.cos.osf.client.support.DateTimeTransform;
import org.dataconservancy.cos.osf.client.support.JodaSupport;
import org.dataconservancy.cos.rdf.annotations.IndividualUri;
import org.dataconservancy.cos.rdf.annotations.OwlIndividual;
import org.dataconservancy.cos.rdf.annotations.OwlProperty;
import org.dataconservancy.cos.rdf.support.OwlClasses;
import org.dataconservancy.cos.rdf.support.OwlProperties;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Links;
import com.github.jasminb.jsonapi.annotations.Type;

/**
 * Comment model for OSF
 *
 * @author khanson
 * @author esm
 */
@Type("comments")
@OwlIndividual(OwlClasses.OSF_COMMENT)
public class Comment {

    /**
     * Unique OSF id for comment
     */
    @Id
    @IndividualUri
    private String id;

    /**
     * content of the comment
     */
    @OwlProperty(OwlProperties.OSF_HAS_CONTENT)
    private String content;

    /**
     * timestamp that the comment was created
     */
    @OwlProperty(value = OwlProperties.OSF_HAS_DATECREATED, transform = DateTimeTransform.class)
    private DateTime date_created;

    /**
     * timestamp when the comment was last updated
     */
    @OwlProperty(value = OwlProperties.OSF_HAS_DATEMODIFIED, transform = DateTimeTransform.class)
    private DateTime date_modified;

    /**
     * has this comment been edited?
     */
    private boolean isModified;

    /**
     * is this comment deleted?
     */
    private boolean isDeleted;

    /**
     * has this comment been marked as abuse?
     */
    private boolean is_abuse;

    /**
     * has this comment been reported?
     */
    private boolean has_report;

    /**
     * does this comment have replies?
     */
    private boolean has_children;

    /**
     * can the current user edit this comment?
     */
    private boolean can_edit;

    /**
     * Gets other links found in data.links:{} section of JSON
     **/
    @Links
    Map<String, ?> links;

    /**
     * pagination links for multiple records
     */
    private org.dataconservancy.cos.osf.client.model.Links pageLinks;

    /**
     * the node this comment belongs to (distinct from the target)
     */
    @Relationship(value = "node", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
    @OwlProperty(OwlProperties.OSF_HAS_NODE)
    private Node node;

    /**
     * API url to replies to this comment
     */
    @Relationship(value = "replies", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
    private String replies;

    /**
     * API url to the target of this comment (i.e. the entity that the comment was placed on)
     */
    @Relationship(value = "target", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.REF)
    private String target;

    /**
     * API url to the target of this comment (i.e. the entity that the comment was placed on), typed as a URI
     */
    @OwlProperty(OwlProperties.OSF_IN_REPLY_TO)
    private URI targetUri;

    /**
     * The user that authored the comment
     */
    @Relationship(value = "user", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
    @OwlProperty(OwlProperties.OSF_HAS_USER)
    private User user;

    /**
     * whether or not this comment is ham
     */
    private boolean is_ham;

    /**
     * The "type" of thing being commented on (e.g. "page", "wiki")
     */
    private String page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_created() {
        if (this.date_created != null) {
            return this.date_created.toString(DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }

    public void setDate_created(String date_created) {
        if (date_created != null) {
            this.date_created = JodaSupport.parseDateTime(date_created);
        } else {
            this.date_created = null;
        }
    }

    public String getDate_modified() {
        if (this.date_modified != null) {
            return this.date_modified.toString(DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }

    public void setDate_modified(String date_modified) {
        if (date_modified != null) {
            this.date_modified = JodaSupport.parseDateTime(date_modified);
        } else {
            date_modified = null;
        }
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(Boolean isModified) {
        this.isModified = isModified;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isIs_abuse() {
        return is_abuse;
    }

    public void setIs_abuse(Boolean is_abuse) {
        this.is_abuse = is_abuse;
    }

    public boolean isHas_children() {
        return has_children;
    }

    public void setHas_children(Boolean has_children) {
        this.has_children = has_children;
    }

    public boolean isCan_edit() {
        return can_edit;
    }

    public void setCan_edit(Boolean can_edit) {
        this.can_edit = can_edit;
    }

    public org.dataconservancy.cos.osf.client.model.Links getPageLinks() {
        return pageLinks;
    }

    @JsonProperty("links")
    public void setPageLinks(org.dataconservancy.cos.osf.client.model.Links pageLinks) {
        this.pageLinks = pageLinks;
    }

    public Map<String, ?> getLinks() {
        return links;
    }

    public void setLinks(Map<String, ?> links) {
        this.links = links;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getTarget() {
        return target;
    }

    /**
     * Sets the target of this comment, which is the URI (typed as a {@code String}) of the thing being commented on.
     * <p>
     * <em>N.B.</em> this method calls {@link #setTargetUri(URI)} to provide a type-safe way of obtaining the URI.
     * </p>
     *
     * @param target the URI of the thing being commented on
     */
    public void setTarget(String target) {
        this.target = target;
        setTargetUri(URI.create(target));
    }

    public URI getTargetUri() {
        return targetUri;
    }

    /**
     * Sets the target of this comment, which is the URI (typed as a {@code URI}) of the thing being commented on.
     * <p>
     * <em>N.B.</em> this method is invoked by {@link #setTarget(String)} to provide a type-safe way of obtaining the
     * URI.
     * </p>
     *
     * @param targetUri the URI of the thing being commented on
     */
    public void setTargetUri(URI targetUri) {
        this.targetUri = targetUri;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean is_ham() {
        return is_ham;
    }

    public void setIs_ham(boolean is_ham) {
        this.is_ham = is_ham;
    }

    public boolean isHas_report() {
        return has_report;
    }

    public void setHas_report(boolean has_report) {
        this.has_report = has_report;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
