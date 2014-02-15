package org.rubychina.app.helper;

import android.text.Editable;
import android.text.Html;
import android.util.Log;

import org.xml.sax.XMLReader;

/**
 * Created by mac on 14-2-15.
 */
public class TagGetter implements Html.TagHandler {
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        Log.v("opening, tag" , opening + " " + tag);
        Log.v("output", output+"");
    }
}
