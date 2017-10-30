# ListCase

Example of Clean Architecture implementation for Android.

In this example you can find how to:

1. make standard list of items with pagination support using [RxJava 2](https://github.com/ReactiveX/RxJava) (new data automatic retrieving while scrolling the list)
2. use [Retrofit](https://github.com/square/retrofit) for networking 
3. use [Moxy](https://github.com/Arello-Mobile/Moxy) for MVP
4. use [Cicerone](https://github.com/terrakok/Cicerone) for navigation
5. use [Dagger 2](https://github.com/google/dagger) for DI
6. organize package structure

## Application description

The application retrieves the list of popular Android repositories from GitHub:
![Alt text](/screenshot.png?raw=true "ListCase screenshot")

Project details web page is opened on list item click. 

License
-----
    Copyright (C) 2017. Alexander Bilchuk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
