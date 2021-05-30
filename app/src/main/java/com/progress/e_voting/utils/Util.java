package com.progress.e_voting.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.progress.e_voting.R;

public class Util {
    public static class ShowProgress {
        private static ShowProgress progress;
        private static ProgressDialog dialog;

        static ShowProgress getInstance() {
            if (ShowProgress.progress == null) {
                ShowProgress.progress = new ShowProgress();
                return ShowProgress.progress;
            }

            return ShowProgress.progress;
        }

        public static void showProgress(Context context, String message) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.show();
        }

        public static void dismissProgress() {
            dialog.dismiss();
        }
    }


    public static class Alert {
        private static Alert alert;

        public static Alert getInstance() {
            if (Alert.alert == null) {
                Alert.alert = new Alert();
                return Alert.alert;
            }
            return Alert.alert;
        }

        public void showAlert(Context context, String message, int layout) {
            View view = LayoutInflater.from(context).inflate(layout, null);
            TextView tv = view.findViewById(R.id.alert_message);
            tv.setText(message);

            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
            dialog
//                    .setMessage(message)
                    .setCancelable(true)
                    .setView(view)
                    .create()
                    .show();
        }
    }
}
