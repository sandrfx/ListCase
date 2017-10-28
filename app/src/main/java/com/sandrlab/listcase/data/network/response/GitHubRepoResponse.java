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
package com.sandrlab.listcase.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public final class GitHubRepoResponse {

    @SerializedName("items")
    private List<GitRepo> items;

    public List<GitRepo> getItems() {
        return items;
    }

    public static final class GitRepo {

        @SerializedName("name")
        private String name;

        @SerializedName("full_name")
        private String fullName;

        @SerializedName("description")
        private String description;

        @SerializedName("html_url")
        private String htmlUrl;

        @SerializedName("owner")
        private Owner owner;

        @SerializedName("stargazers_count")
        private int starGazersCount;

        @SerializedName("watchers_count")
        private int watchersCount;

        public String getName() {
            return name;
        }

        public String getFullName() {
            return fullName;
        }

        public String getDescription() {
            return description;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public Owner getOwner() {
            return owner;
        }

        public int getStarGazersCount() {
            return starGazersCount;
        }

        public int getWatchersCount() {
            return watchersCount;
        }

        public static final class Owner {

            @SerializedName("login")
            private String login;

            @SerializedName("avatar_url")
            private String avatarUrl;

            public String getLogin() {
                return login;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }
        }
    }
}
