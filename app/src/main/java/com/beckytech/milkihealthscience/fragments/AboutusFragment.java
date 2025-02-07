package com.beckytech.milkihealthscience.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beckytech.milkihealthscience.R;

public class AboutusFragment extends Fragment {

    private WebView homeWebView;
    private ProgressBar progressbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
        homeWebView = view.findViewById(R.id.home_webView);
        progressbar = view.findViewById(R.id.progressbar);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);

        homeWebView.getSettings().setSupportZoom(false);
        homeWebView.getSettings().setJavaScriptEnabled(true);
        homeWebView.getSettings().setDomStorageEnabled(true);
        homeWebView.loadUrl("https://milkihealthscience.com/about/");
        homeWebView.setWebChromeClient(new WebChromeClient());
        homeWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(requireContext(), "Error loading page: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, request, error);
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage(R.string.ssl_error_message) // Provide a user-friendly message in strings.xml
                        .setPositiveButton(R.string.proceed, (dialog, which) -> handler.proceed())
                        .setNegativeButton(R.string.cancel, (dialog, which) -> handler.cancel());
                builder.create().show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                homeWebView.loadUrl("https://milkihealthscience.com/about/");
            }, 1500);
        });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright, requireContext().getTheme()),
                getResources().getColor(android.R.color.holo_orange_dark, requireContext().getTheme()),
                getResources().getColor(android.R.color.holo_green_dark, requireContext().getTheme()),
                getResources().getColor(android.R.color.holo_red_dark, requireContext().getTheme())
        );

        if (savedInstanceState != null)
            homeWebView.restoreState(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (homeWebView.canGoBack())
                    homeWebView.goBack();
                else {
                    setEnabled(false);
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        homeWebView.saveState(outState);
    }
}