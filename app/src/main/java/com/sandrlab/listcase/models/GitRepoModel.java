/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sandrlab.listcase.models;

import android.support.annotation.NonNull;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public final class GitRepoModel {

    private final String name;
    private final String fullName;
    private final String description;
    private final String avatarUrl;
    private final String htmlUrl;
    private final String owner;
    private final int stars;
    private final int watchers;

    public GitRepoModel(@NonNull String name,
                        @NonNull String fullName,
                        @NonNull String description,
                        @NonNull String avatarUrl,
                        @NonNull String htmlUrl,
                        @NonNull String owner,
                        int stars,
                        int watchers) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.owner = owner;
        this.stars = stars;
        this.watchers = watchers;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getOwner() {
        return owner;
    }

    public int getStars() {
        return stars;
    }

    public int getWatchers() {
        return watchers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitRepoModel that = (GitRepoModel) o;

        if (stars != that.stars) return false;
        if (watchers != that.watchers) return false;
        if (!name.equals(that.name)) return false;
        if (!fullName.equals(that.fullName)) return false;
        if (!description.equals(that.description)) return false;
        if (!avatarUrl.equals(that.avatarUrl)) return false;
        if (!htmlUrl.equals(that.htmlUrl)) return false;
        return owner.equals(that.owner);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + avatarUrl.hashCode();
        result = 31 * result + htmlUrl.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + stars;
        result = 31 * result + watchers;
        return result;
    }

    @Override
    public String toString() {
        return "GitRepoModel{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", owner='" + owner + '\'' +
                ", stars=" + stars +
                ", watchers=" + watchers +
                '}';
    }
}
