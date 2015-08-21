To "prove" that dicent rolls are fair, I wrote a little test program. Here is the code I used:

```
import com.dicent.dice.DieData;

public class ProbabilityTest {
	static int rolled1, rolled2, rolled3, rolled4, rolled5, rolled6;
    static final int n = 100000;
    
	public static void main(String[] args) {
		rolled1 = rolled2 = rolled3 = rolled4 = rolled5 = rolled6 = 0;
		
		DieData die = DieData.create(DieData.RED_DIE);
		
		for (int i = 1; i <= n; i++) {
			die.roll();
            switch (die.side) {
            case SIDE1:
            	rolled1++;
            	break;
            case SIDE2:
            	rolled2++;
            	break;
            case SIDE3:
            	rolled3++;
            	break;
            case SIDE4:
            	rolled4++;
            	break;
            case SIDE5:
            	rolled5++;
            	break;
            case SIDE6:
            	rolled6++;
            	break;
            }
		}
		
		System.out.println("n: " + n);
		System.out.println("side 1: " + rolled1);
		System.out.println("side 2: " + rolled2);
		System.out.println("side 3: " + rolled3);
		System.out.println("side 4: " + rolled4);
		System.out.println("side 5: " + rolled5);
		System.out.println("side 6: " + rolled6);
	}

}
```

This bascally just creates a die, rolls it n times and counts how often each side was rolled.
Here are some test results:

```
n: 100
side 1: 19
side 2: 19
side 3: 15
side 4: 18
side 5: 16
side 6: 13
```

```
n: 1000
side 1: 162
side 2: 161
side 3: 166
side 4: 153
side 5: 170
side 6: 188
```

```
n: 10000
side 1: 1711
side 2: 1743
side 3: 1628
side 4: 1613
side 5: 1605
side 6: 1700
```

```
n: 100000
side 1: 16602
side 2: 16776
side 3: 16591
side 4: 16757
side 5: 16833
side 6: 16441
```

```
n: 1000000
side 1: 166275
side 2: 166454
side 3: 166966
side 4: 166916
side 5: 166808
side 6: 166581
```

you should get different but similar numbers if you repeat the test.
As you can see, the more rolls you make, the more it evens out which is exactly what you would expect from a random roll ;)