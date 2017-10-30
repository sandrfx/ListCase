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
package com.sandrlab.listcase.di.repos_list;

import com.sandrlab.listcase.di.app.AppComponent;
import com.sandrlab.listcase.presentation.repos_list.view.GitReposListActivity;

import dagger.Component;
import ru.terrakok.cicerone.NavigatorHolder;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
@ReposListScope
@Component(
        dependencies = AppComponent.class,
        modules = ReposListModule.class)
public interface ReposListComponent {

    void inject(GitReposListActivity activity);
    void inject(NavigatorHolder navigatorHolder);
}
