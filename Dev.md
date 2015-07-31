> When the svn java sources are modified, a conflict is created within the subversion package maintenance downlink.

> To manage these few conflicts, it would be best to perform a backup, revert, update, and restore procedure.

> Copy the current conflict modified files out of their locations to short term storage, e.g.
```
  tar cvf /tmp gap-data/trunk $(cat mods.flist)
```

> Revert the files using
```
  svn revert
```

> Update the package at maintenance points, major or minor number changes, using
```
  svn update
```
> Restore by writing the modified files from the short store over the package repository.
```
  tar xfp /tmp/tmp
```


> # Conflicting files #

> The only conflicting files should be [Application](Application.md) and
```
 gap-data/trunk/web/WEB-INF/templates
```