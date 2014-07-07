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

### Data Events

### Data Mappings
