package com.enioka.scanner.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.enioka.scanner.R;
import com.enioka.scanner.api.Scanner;
import com.enioka.scanner.data.Barcode;
import com.enioka.scanner.data.BarcodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper fragment designed to take soft keyboard input (in case camera or laser are broken).
 * Hooked on the Scanner.ScannerDataCallback interface which the main activity should already implement.
 */
public class ManualInputFragment extends DialogFragment {

    private Scanner.ScannerDataCallback cb;
    private Boolean closeOnValidation;
    private int inviteTextId;

    public static ManualInputFragment newInstance() {
        return newInstance(true, R.string.fragment_scan_manual_invite);
    }

    public static ManualInputFragment newInstance(Boolean closeOnValidation) {
        return newInstance(closeOnValidation, R.id.scanner_manual_invite);
    }

    public static ManualInputFragment newInstance(Boolean closeOnValidation, int inviteTextId) {
        ManualInputFragment res = new ManualInputFragment();
        Bundle b = new Bundle(1);
        b.putBoolean("CLOSE_ON_VALIDATION", closeOnValidation);
        b.putInt("INVITE_TEXT_ID", inviteTextId);
        res.setArguments(b);
        return res;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            // The host should be an activity implementing a barcode listener.
            cb = (Scanner.ScannerDataCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Scanner.ScannerDataCallback");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        closeOnValidation = getArguments().getBoolean("CLOSE_ON_VALIDATION", true);
        inviteTextId = getArguments().getInt("INVITE_TEXT_ID", R.string.fragment_scan_manual_invite);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_scan_manual_input, null);
        builder.setView(view);

        final Dialog res = builder.create();

        res.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        view.findViewById(R.id.scanner_manual_bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = ((TextView) view.findViewById(R.id.scanner_manual_input)).getText().toString();
                if (txt.isEmpty()) {
                    return;
                }

                List<Barcode> res = new ArrayList<Barcode>(1);
                Barcode b = new Barcode(txt, BarcodeType.UNKNOWN);
                res.add(b);
                cb.onData(res);

                // Do not always dismiss - let the host do it in some cases (it may want to validate the data).
                if (closeOnValidation) {
                    dismiss();
                }
            }
        });

        ((EditText) view.findViewById(R.id.scanner_manual_input)).setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String txt = ((TextView) view.findViewById(R.id.scanner_manual_input)).getText().toString();
                    if (txt.isEmpty()) {
                        return false;
                    }

                    List<Barcode> res = new ArrayList<Barcode>(1);
                    Barcode b = new Barcode(txt, BarcodeType.UNKNOWN);
                    res.add(b);
                    cb.onData(res);

                    if (closeOnValidation) {
                        dismiss();
                    }

                    return true;
                }
                return false;
            }
        });

        ((TextView) view.findViewById(R.id.scanner_manual_invite)).setText(getResources().getString(inviteTextId));

        return res;
    }
}