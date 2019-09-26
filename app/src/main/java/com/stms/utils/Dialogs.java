package com.stms.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.stms.R;


/**
 * This is common class used t display dialogs
 */
public class Dialogs {

    /**
     * Show Alert With message and Title is Alert Always
     *
     * @param context = context
     * @param message = message for alert
     */
    public void showAlert(final Context context,
                          String message) {
        showAlert(context, context.getString(R.string.alert_title), message);

    }


    /**
     * Show Alert With message and Title is Alert Always
     *
     * @param context = context
     * @param message = message for alert
     */
    public void showAlert(final Context context,
                          int message) {
        showAlert(context, context.getString(R.string.alert_title), context.getString(message));

    }

    /**
     * Show Alert With  custom message and Title
     *
     * @param context = context
     * @param message = custom message for alert
     * @param title   = Custom title for alert
     */
    public void showAlert(final Context context, String title,
                          final String message) {
       /* setTitle(title).*/


        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new AlertDialog.Builder(context).setMessage(message)
                        .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false).create().show();
            }
        });

    }


    /**
     * Alert for passing Activity to back
     *
     * @param context = context
     * @param message = custom message for alert
     */
    public void showAlertFinish(final Context context,
                                String message) {
        showAlertFinish(context, context.getString(R.string.alert_title), message);
    }

    /**
     * Alert for passing Activity to back
     *
     * @param context = context
     * @param message = custom message for alert
     * @param title   = Custom title for alert
     */
    public void showAlertFinish(final Context context, String title,
                                final String message) {

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(context).setMessage(message)
                        .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dialog.dismiss();
                                ((Activity) context).finish();
                            }
                        }).create().show();
            }
        });

    }

    /**
     * Alert for single button with title as alert , positive button as ok
     *
     * @param context        =  context
     * @param message        = custom message for alert
     * @param alertInterface == call back
     */
    public void showAlertForMessage(Context context,
                                    String message,
                                    final AlertInterface alertInterface) {


        showAlertForMessage(context,
                context.getString(R.string.alert_title)
                , message, context.getString(R.string.alert_ok), alertInterface);


    }


    /**
     * Alert for single button  , positive button as ok
     *
     * @param context        =  context
     * @param title          == Custom title for alert
     * @param message        = custom message for alert
     * @param alertInterface == call back
     */
    public void showAlertForMessage(Context context, String title,
                                    String message,
                                    final AlertInterface alertInterface) {

        showAlertForMessage(context,
                title,
                message, context.getString(R.string.alert_ok), alertInterface);
    }


    /**
     * Alert for single button
     *
     * @param context        =  context
     * @param title          == Custom title for alert
     * @param message        = custom message for alert
     * @param buttonTitle=   custom title for button
     * @param alertInterface == call back
     */
    public void showAlertForMessage(final Context context, final String title,
                                    final String message, final String buttonTitle,
                                    final AlertInterface alertInterface) {


        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // alertDialog.setTitle(title);

                alertDialog.setMessage(message);
                alertDialog.setCancelable(false);

                alertDialog.setIcon(null);

                alertDialog.setPositiveButton(buttonTitle,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                alertInterface.buttonYesClick();
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });


    }


    /**
     * Default dialog with two custom buttons Title is Alert Always and OK , CANCEL button
     *
     * @param context        = context
     * @param message        = custom message for alert
     * @param alertInterface = call back
     */


    public void showAlertForAction(Context context,
                                   String message,
                                   final AlertInterface alertInterface) {


        showAlertForAction(context,
                message, context.getString(R.string.alert_ok),
                context.getString(R.string.alert_cancel),
                alertInterface);


    }


    /**
     * Default dialog with two custom buttons Title is Alert Always and OK , CANCEL button
     *
     * @param context        = context
     * @param message        = custom message for alert
     * @param alertInterface = call back
     */


    public void showAlertForAction(final Context context, final String title,
                                   final String message, final String button1, final String button2,
                                   final AlertInterface alertInterface) {


        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                if (title != null)
                    alertDialog.setTitle(title);


                alertDialog.setCancelable(false);
                alertDialog.setMessage(message);

                alertDialog.setPositiveButton(button1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertInterface.buttonYesClick();
                    }
                });
                alertDialog.setNegativeButton(button2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertInterface.buttonNoClick();
                    }
                });
                alertDialog.show();
            }
        });


    }


    /**
     * Default dialog with two custom buttons
     *
     * @param context        = context
     * @param message        = custom message for alert
     * @param button1        = Positive button for alert
     * @param button2        = Negative button for alert
     * @param alertInterface = call back
     */


    public void showAlertForAction(final Context context,
                                   final String message, final String button1, final String button2,
                                   final AlertInterface alertInterface) {

        showAlertForAction(context, null, message, button1, button2, alertInterface);

    }


}
