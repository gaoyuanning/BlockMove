import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class PythonCall {

	public static String[] getSequence(int count) {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile("src/superBlock.py");
		PyFunction func = (PyFunction) interpreter.get("solveBlock",
				PyFunction.class);
		StringBuffer stringBuffer = new StringBuffer();
		String initString = new String();
		int i;
		for(i = 0; i < count; i++) {
			stringBuffer.append("B");
		}
		for(; i < count * 2; i++) {
			stringBuffer.append("W");
		}
		stringBuffer.append("E");
		initString = stringBuffer.toString();
		PyObject pyObject = func.__call__(new PyString(initString), new PyInteger(count));
		String resultString = pyObject.toString();
		String[] resultArray = resultString.substring(1,
				resultString.length() - 1).split(", ");
		return resultArray;
	}
}
