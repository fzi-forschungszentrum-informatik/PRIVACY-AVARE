/*
    	Copyright 2016-2018 AVARE project team

        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).

        Files under this folder (and the subfolders) with "Created by AVARE Project ..."-Notice
	    are our work and licensed under Apache Licence, Version 2.0"

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package app.avare.manager;

import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.content.pm.PackageManager.NameNotFoundException;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import com.facebook.react.uimanager.annotations.ReactProp;

public class RNIconManager extends SimpleViewManager<ImageView> {

    public static final String REACT_CLASS = "RNIconView";
    private ThemedReactContext mContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public ImageView createViewInstance(ThemedReactContext context) {
        mContext = context;
        ImageView img = new ImageView(context);
        return img;
    }

    @ReactProp(name = "package")
    public void setPackage(ImageView view, String pkg) {
        try {
            Drawable icon = mContext.getPackageManager().getApplicationIcon(pkg);
            view.setImageDrawable(icon);
        } catch (NameNotFoundException ne) {
            //TODO: set sensible default icon
        }
    }
}