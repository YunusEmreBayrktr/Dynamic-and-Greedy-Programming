import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Main {
    /**
     * Propagated {@link IOException} here
     * {@link #parseJSON} method should be called here
     * A {@link Planner} instance must be instantiated here
     * @throws ParseException 
     */
    public static void main(String[] args) throws IOException{
        String file = args[0];    //get file name as an argument
        Task[] tasks = parseJSON(file);
        Arrays.sort(tasks);           //sort task array for binary search
        
        Planner planner = new Planner(tasks);
        planner.planDynamic();
        planner.planGreedy();
        
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Task}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    public static Task[] parseJSON(String filename) throws FileNotFoundException {

        /* JSON parsing operations here */
        Gson gson = new Gson();
        JsonReader jr = new JsonReader(new FileReader(filename));
        return gson.fromJson(jr, Task[].class);
    }
}
