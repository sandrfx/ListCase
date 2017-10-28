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
package com.sandrlab.listcase.presentation.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sandrlab.listcase.R;

/**
 * @author Alexander Bilchuk (a.bilchuk@sandrlab.com)
 */
public final class ImageUtils {

    public static void displayImageFromURL(@NonNull Context context,
                                           @NonNull String imageURL,
                                           @NonNull ImageView view) {
        if (TextUtils.isEmpty(imageURL)) {
            view.setImageResource(R.color.placeholder);
            return;
        }

        Glide.with(context).load(imageURL).into(view);
    }
}
