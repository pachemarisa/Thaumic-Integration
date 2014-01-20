Aether Coding Style Guidelines
================================

```java
public class Hello implements IFriendly
{

    public float sinage; // place all fields before methods
    ...
}
```

```java
if (this.hurtTime > 0)
{
    this.sinage += 0.9F; // use the "this" qualifier for method and field access
    this.onUpdate();
}
else if (this.dum)
{
    this.sinage += 0.15F;
}
else
{
    this.sinage += 0.05F;
}
```


```java
@Override
protected void jump() // use the override annotation
{
}
```

Note: for automatic correction of the coding style, use eclipse_cleanup.xml and eclipse_formatter.xml