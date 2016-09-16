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

import static org.dataconservancy.cos.osf.client.support.JodaSupport.DATE_TIME_FORMATTER_ALT;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.dataconservancy.cos.osf.client.support.DateTimeTransform;
import org.dataconservancy.cos.osf.client.support.DownloadLinkTransform;
import org.dataconservancy.cos.osf.client.support.FileIdTransform;
import org.dataconservancy.cos.osf.client.support.JodaSupport;
import org.dataconservancy.cos.osf.client.support.ProviderIdTransform;
import org.dataconservancy.cos.rdf.annotations.IndividualUri;
import org.dataconservancy.cos.rdf.annotations.OwlIndividual;
import org.dataconservancy.cos.rdf.annotations.OwlProperty;
import org.dataconservancy.cos.rdf.support.OwlClasses;
import org.dataconservancy.cos.rdf.support.OwlProperties;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.RelType;
import com.github.jasminb.jsonapi.ResolutionStrategy;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Links;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;


/**
 * Wiki model for OSF
 * Created by Ben Trumbore on 9/16/2016.
 */
//@Type("wiki")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@OwlIndividual(OwlClasses.OSF_WIKI)
public class Wiki {

    /*unique OSF ID for the file**/
    //@Id
    //@IndividualUri(transform = ProviderIdTransform.class)
    private String id;

    /**list of comments associated with wiki*/
    //@Relationship(value = "comments", resolve = true, relType = RelType.RELATED, strategy = ResolutionStrategy.OBJECT)
    private List<Comment> comments;

    /**Gets other links found in data.links:{} section of JSON**/
    //@Links
    //@OwlProperty(value = OwlProperties.OSF_HAS_BINARYURI, transform = DownloadLinkTransform.class)
    Map<String, ?> links;

    /**name of the wiki; used for display*/
    //@OwlProperty(OwlProperties.OSF_HAS_NAME)
    private String name;

    /**"file" or "folder"*/
    //@OwlProperty(OwlProperties.OSF_HAS_HASKIND)
    private String kind;

    /**the unix-style path to the file relative to the provider root*/
    //@OwlProperty(OwlProperties.OSF_HAS_MATERIALIZEDPATH)
    private String materialized_path;

    /**timestamp of when this wiki was last updated*/
    //@OwlProperty(value = OwlProperties.OSF_HAS_DATEMODIFIED, transform = DateTimeTransform.class)
    private DateTime date_modified;

    /**node this wiki belongs to*/
    //@OwlProperty(OwlProperties.OSF_HAS_NODE)
    private String node;

    /**user that owns this wiki*/
    //@OwlProperty(OwlProperties.OSF_HAS_NODE)
    private String user;

    /**same as for corresponding WaterButler entity*/
    //@OwlProperty(OwlProperties.OSF_HAS_PATH)
    private String path;

    /**  */
    //@OwlProperty(OwlProperties.OSF_HAS_PATH)
    private String content_type;

    /**version of wiki*/
    //@OwlProperty(OwlProperties.OSF_HAS_SIZE)
    private Integer version;

    /**size of wiki in bytes, null for folders*/
    //@OwlProperty(OwlProperties.OSF_HAS_SIZE)
    private Integer size;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialized_path() {
        return materialized_path;
    }

    public void setMaterialized_path(String materialized_path) {
        this.materialized_path = materialized_path;
    }

    public String getDate_modified() {
        if (this.date_modified!=null) {
            return this.date_modified.toString(DATE_TIME_FORMATTER_ALT);
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return content_type;
    }

    public void setContentType(String content_type) {
        this.content_type = content_type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
