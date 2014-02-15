package org.rubychina.app.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import org.rubychina.app.utils.ApiUtils;

import java.util.regex.Pattern;

/**
 * Created by mac on 14-2-15.
 */
public class CustomLinkMovementMethod extends LinkMovementMethod {

    public interface CustomLinkMovementMethodCallback {
        public void onClick();
    }

    private static Context movementContext;

    private static CustomLinkMovementMethod linkMovementMethod = new CustomLinkMovementMethod();

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                String url = link[0].getURL();
                Log.v("url: ", url);
                if (url.contains("http"))
                {
                    Toast.makeText(movementContext, "Link was clicked", Toast.LENGTH_LONG).show();
                    movementContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else if (isLoginName(url.replace("/", ""))){
                    Toast.makeText(movementContext, "LoginName was clicked", Toast.LENGTH_LONG).show();
                } else if (url.contains("#")) {
                    Toast.makeText(movementContext, url, Toast.LENGTH_LONG).show();
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }

    public static android.text.method.MovementMethod getInstance(Context c) {
        movementContext = c;
        return linkMovementMethod;
    }

    public static boolean isLoginName(String str){
        Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$");
        return pattern.matcher(str).matches();
    }
}
