
/**
 * @author Darkness 
 * create on 2010-12-8 下午01:48:51
 * @version 1.0
 * @since JDP 1.0
 */

public class TankTest {
	static void printInfo(Class<?> cc) {
		System.out.println("Class name: " + cc.getName() + " is interface? ["
				+ cc.isInterface() + "]");
		System.out.println("Simple name: " + cc.getSimpleName());
		System.out.println("Canonical name : " + cc.getCanonicalName());
		System.out.println();
	}

	public static void main(String[] args) {
		Class<?> c = null;
		try {
			c = Class.forName("FancyTank");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find FancyTank");
			System.exit(1);
		}
		printInfo(c);
		for (Class<?> face : c.getInterfaces())
			printInfo(face);
		Class<?> up = c.getSuperclass();
		Object obj = null;
		try {
			// Requires default constructor:
			obj = up.newInstance();
		} catch (InstantiationException e) {
			System.out.println("Cannot instantiate");
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("Cannot access");
			System.exit(1);
		}
		printInfo(obj.getClass());
	}
}
