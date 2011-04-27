//: typeinfo/toys/GenericToyTest.java
// Testing class Class.

public class GenericToyTest {
	public static void main(String[] args) throws Exception {
		Class<FancyTank> ftClass = FancyTank.class;
		// Produces exact type:
		FancyTank fancyTank = ftClass.newInstance();
		Class<? super FancyTank> up = ftClass.getSuperclass();
		// This won't compile:
		// Class<Toy> up2 = ftClass.getSuperclass();
		// Only produces Object:
		Object obj = up.newInstance();
	}
} // /:~
