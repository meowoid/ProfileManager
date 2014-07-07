## Profile Manager Documentation

### Using the Library

The profile data store singleton is accessed:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
```

The key operations that you can do with it are defined in the [ProfileInterface](https://github.com/xsenselabs/ProfileManager/blob/master/src/com/ubhave/profilemanager/ProfileInterface.java) class.

### Data Distributions

The library assumes that each distribution has a unique name (e.g., 'Fruits'). Each entry in a distribution is also a unique key (e.g., 'Bananas', 'Apples'), and has a particular value. You can set the value of a distribution entry by using ```addToDistribution```. For example:

```
ProfileDataStore profileManager = ProfileDataStore.getInstance(context);
profileManager.addToDistribution('Fruits', 'Bananas', 12);
```

Will add 12 units to the 'Bananas' entry of the 'Fruits' distribution (note: this is adding, not setting!).

To display a ```Distribution``` in your app, you need to extend the [DistributionActivity](https://github.com/xsenselabs/ProfileManager/blob/master/src/com/ubhave/profilemanager/ui/distribution/DistributionActivity.java) and [DistributionListAdapter](https://github.com/xsenselabs/ProfileManager/blob/master/src/com/ubhave/profilemanager/ui/distribution/DistributionListAdapter.java) classes. These are abstract classes that implement the functionality of asynchronously loading and displaying a distribution.

The ```DistributionActivity``` requires:

* ```String getDistributionTitle()```: a String that is is displayed at the top of the UI as a title (e.g., 'Your Fruit Consumption').
* ```String getDistributionVariableName()```: a String that is used to query the ```ProfileDataStore```. In the example above, this is 'Fruits'.
* ```void onNoDataAvailable()```: what your app should do if the ```Distribution``` is empty. For example, it may ```finish()``` the activity.
* ```View getNoDataView()```: the ```View``` to display should there be no ```Distribution``` data.
* ```int getLayoutId()```: The layout id of your activity (i.e., R.layout.[your-layout]). This layout may contain the following items which will be automatically populated:
* ```TextView getScreenTitle()```: is populated with the value of ```getDistributionTitle()```.
* ```ListView getListView()```: is the ```ListView``` that actually displays the distribution.
* ```ProgressBar getLoadingProgressBar()```: the ```ProgressBar``` to display while the distribution is being loaded from the database.

The ```DistributionListAdapter``` requires:

* (Constructor): the layout that will be inflated for list items (i.e., R.layout.[your-layout]).
* ```TextView getEntryText(View row)```: the label part of the row. In the example above, it would be set to 'Bananas'.
* ```TextView getLabelTextView(View row)```: the percentage part of the row. In the example above, if the distribution value for 'Bananas is 12, and the sum of all the 'Fruits' values is 24, this label will be set to '50%'.
* ```ProgressBar getEntryProgressBar(View row)```: the progress bar that will, in our example, be set to 50% to visually show the value of the distribution.

### Data Events

### Data Mappings
