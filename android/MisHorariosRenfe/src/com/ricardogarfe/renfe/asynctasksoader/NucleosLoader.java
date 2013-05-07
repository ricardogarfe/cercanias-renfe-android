/*
 * Copyright [2013] [Ricardo García Fernández] [ricardogarfe@gmail.com]
 * 
 * This file is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ricardogarfe.renfe.asynctasksoader;

import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.ricardogarfe.renfe.model.NucleoCercanias;
import com.ricardogarfe.renfe.services.parser.JSONNucleosCercaniasParser;

/**
 * @author ricardo
 * 
 */
public class NucleosLoader extends AsyncTaskLoader<List<NucleoCercanias>> {

    private JSONNucleosCercaniasParser mJSONNucleosCercaniasParser;

    private List<NucleoCercanias> mNucleoCercaniasList;

    private String TAG = getClass().getSimpleName();

    /*********************************************************************/
    /** Observer which receives notifications when the data changes **/
    /*********************************************************************/

    // NOTE: Implementing an observer is outside the scope of this post (this
    // example
    // uses a made-up "SampleObserver" to illustrate when/where the observer
    // should
    // be initialized).

    // The observer could be anything so long as it is able to detect content
    // changes
    // and report them to the loader with a call to onContentChanged(). For
    // example,
    // if you were writing a Loader which loads a list of all installed
    // applications
    // on the device, the observer could be a BroadcastReceiver that listens for
    // the
    // ACTION_PACKAGE_ADDED intent, and calls onContentChanged() on the
    // particular
    // Loader whenever the receiver detects that a new application has been
    // installed.
    // Please don’t hesitate to leave a comment if you still find this
    // confusing! :)

//    private SampleObserver mObserver;

    public NucleosLoader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * A task that performs the asynchronous load
     */
    @Override
    public List<NucleoCercanias> loadInBackground() {
        // TODO Auto-generated method stub

        List<NucleoCercanias> nucleoCercaniasList = null;
        try {
            nucleoCercaniasList = getmJSONNucleosCercaniasParser()
                    .retrieveNucleoCercaniasFromJSON(
                            JSONNucleosCercaniasParser.NUCLEOS_JSON, true);
        } catch (Exception e) {
            Log.e(TAG, "Parsing JSON data:\t" + e.getMessage());
        }

        return nucleoCercaniasList;
    }

    /**
     * Deliver the results to the registered listener
     */
    @Override
    public void deliverResult(List<NucleoCercanias> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the
            // data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<NucleoCercanias> oldData = mNucleoCercaniasList;
        mNucleoCercaniasList = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    /**
     * Implement the Loader’s state-dependent behavior
     */
    @Override
    protected void onStartLoading() {
        if (mNucleoCercaniasList != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mNucleoCercaniasList);
        }

        // Begin monitoring the underlying data source.
//        if (mObserver == null) {
//            mObserver = new SampleObserver();
//            // TODO: register the observer
//        }

        if (takeContentChanged() || mNucleoCercaniasList == null) {
            // When the observer detects a change, it should call
            // onContentChanged()
            // on the Loader, which will cause the next call to
            // takeContentChanged()
            // to return true. If this is ever the case (or if the current data
            // is
            // null), we force a new load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mNucleoCercaniasList != null) {
            releaseResources(mNucleoCercaniasList);
            mNucleoCercaniasList = null;
        }

        // The Loader is being reset, so we should stop monitoring for changes.
//        if (mObserver != null) {
//            // TODO: unregister the observer
//            mObserver = null;
//        }
    }

    @Override
    public void onCanceled(List<NucleoCercanias> data) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources(data);
    }

    private void releaseResources(List<NucleoCercanias> data) {
        // For a simple List, there is nothing to do. For something like a
        // Cursor, we
        // would close it in this method. All resources associated with the
        // Loader
        // should be released here.
    }

    public JSONNucleosCercaniasParser getmJSONNucleosCercaniasParser() {
        return mJSONNucleosCercaniasParser;
    }

    public void setmJSONNucleosCercaniasParser(
            JSONNucleosCercaniasParser mJSONNucleosCercaniasParser) {
        this.mJSONNucleosCercaniasParser = mJSONNucleosCercaniasParser;
    }

}
