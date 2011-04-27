import java.util.Arrays;
import java.util.List;

/**
 * @author Darkness create on 2010-12-7 下午04:31:03
 * @version 1.0
 * @since JDP 1.0
 */
interface HasBatteries {
}

interface Waterproof {
}

interface Shoots {
}

public class Tank {
	static {// Examination of the way the class loader works.
		System.out.println("Loading Tank");
	}

	// Comment out the following default constructor
	// to see NoSuchMethodError from (*1*)

	public Tank() {

	}

	public Tank(int i) {
	}

	void draw() {
		System.out.println(this + ".draw()");
	}

	public static void main(String[] args) {

		try {
			Class.forName("Tank");
		} catch (ClassNotFoundException e) {
		}

		List<Tank> tankList = Arrays.asList(new RobotTank(), new GamerTank());
		for (Tank tank : tankList) {
			tank.draw();
		}
	}
}

class RobotTank extends Tank {
	static {
		System.out.println("Loading RobotTank");
	}

	public String toString() {
		return "RobotTank";
	}
}

class GamerTank extends Tank {
	static {
		System.out.println("Loading GamerTank");
	}

	public String toString() {
		return "GamerTank";
	}
}

class FancyTank extends Tank implements HasBatteries, Waterproof, Shoots {
	FancyTank() {
		super(1);
	}
}

