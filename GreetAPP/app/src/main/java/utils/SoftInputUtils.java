package utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class SoftInputUtils {
    private SoftInputUtils() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean showSoftInput(@NonNull View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                    (Context.INPUT_METHOD_SERVICE));
            if (imm != null) {
                return imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
        return false;
    }

    public static boolean hideSoftKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

}
