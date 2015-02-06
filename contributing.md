## Working on AOSP

#### Downloading source

Follow instructions at https://source.android.com/source/downloading.html except those noted below. You need to set up authentication to be able to submit changes.
DroidDriver is an "unbundled" project. If you specify the repo manifest branch "droiddriver-dev" (see below), you'll get only the relevant projects instead of the whole AOSP tree.

Create a dir for AOSP, e.g. ~/android/aosp. It should be separate from your work on the internal repo to avoid confusion.
Then get a local client of the repo:

```bash
$ mkdir droiddriver-dev
$ cd droiddriver-dev
$ repo init -u https://android.googlesource.com/a/platform/manifest -b droiddriver-dev
$ repo sync
```

The code should be downloaded to the current dir. You may see some lines in the output like:
curl: (22) The requested URL returned error: 401 Unauthorized
These messages seem non-fatal and you should see these dirs after it is done:
build/  external/  frameworks/  Makefile  prebuilts/

#### Building

This sets up environment and some bash functions, particularly "tapas"
(the counterpart of "lunch" for unbundled projects) and "m".

```bash
$ . build/envsetup.sh
$ tapas droiddriver ManualDD
$ m
```

ManualDD is an APK you can use to manually test DroidDriver.
