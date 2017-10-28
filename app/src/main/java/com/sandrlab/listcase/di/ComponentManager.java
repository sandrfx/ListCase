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
package com.sandrlab.listcase.di;

import com.sandrlab.listcase.di.app.AppComponent;
import com.sandrlab.listcase.di.app.DaggerAppComponent;
import com.sandrlab.listcase.di.repos_list.DaggerReposListComponent;
import com.sandrlab.listcase.di.repos_list.ReposListComponent;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public final class ComponentManager {

    private static volatile ComponentManager instance;
    private volatile AppComponent appComponent;
    private volatile ReposListComponent reposListComponent;

    public static ComponentManager getInstance() {
        if (instance == null) {
            synchronized (ComponentManager.class) {
                if (instance == null) {
                    instance = new ComponentManager();
                }
            }
        }
        return instance;
    }

    private ComponentManager() {}

    public synchronized void initAppComponent() {
        appComponent = DaggerAppComponent.builder().build();
    }

    public synchronized AppComponent getAppComponent() {
        return appComponent;
    }

    public synchronized ReposListComponent getReposListComponent() {
        if (reposListComponent == null) {
            reposListComponent = DaggerReposListComponent.builder()
                    .appComponent(getAppComponent())
                    .build();
        }
        return reposListComponent;
    }

    public synchronized void cleatReposListComponent() {
        reposListComponent = null;
    }
}
