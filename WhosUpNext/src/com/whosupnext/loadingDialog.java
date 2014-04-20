package com.whosupnext;

import android.app.ProgressDialog;
import android.content.Context;

public class loadingDialog
{
	private ProgressDialog mDialog;
	private Context mContext;
	
	public loadingDialog(Context c)
	{
		mContext = c;
	}

	public void startLoading()
	{
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage("loading...");
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setCancelable(false);
		mDialog.show();
	}
	
	public void stopLoading()
	{
		mDialog.dismiss();
		mDialog = null;
	}
}
