import com.fasterxml.jackson.databind.ObjectMapper;
//import jdk.nashorn.internal.parser.JSONParser;
import models.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Students {


    public static void main(String[] args) throws IOException, ParseException {

        Set<Student> studentSet = new HashSet<Student>();

        studentSet.add(new Student(1L, "jack", "black"));
        studentSet.add(new Student(2L, "johnny", "cage"));
        studentSet.add(new Student(3L, "nick", "white"));
        studentSet.add(new Student(4L, "kevin", "naughton"));
        studentSet.add(new Student(5L, "garry", "potter"));
        studentSet.add(new Student(6L, "peter", "parker"));
        studentSet.add(new Student(7L, "kate", "williams"));

        // this is file with given pathName
        File file = new File("C:/Users/cshar/OneDrive/Desktop/data.json");

        JSONArray jsonArray = new JSONArray();
        // writing to data.json file.
        FileWriter fileWriter = new FileWriter(file);
        studentSet.forEach(student -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", student.getId());
            jsonObject.put("firstName", student.getFirstName());
            jsonObject.put("lastName", student.getLastName());
//            jsonArray.add(jsonObject);
            try {
                fileWriter.write(jsonObject.toJSONString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        /*
        // writing json-array
        try {
            fileWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        fileWriter.close();


       /* // reading from data.json file
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        // reading file line by line
        String line;
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }*/

        // stream is inputStream
        // reading file all line in once
        FileInputStream stream = new FileInputStream(file);
        Stream<String> lines = new BufferedReader(new InputStreamReader(stream)).lines();
        ArrayList<Map<String, String>> list = new ArrayList<>();

        Consumer<? super Object> consumer = new Consumer<Object>() {
            @Override
            public void accept(Object o) {
//                System.out.println((Student) o);
                list.add((Map) o);
            }
        };

        JSONParser parser = new JSONParser();
        ContainerFactory containerFactory = new ContainerFactory() {
            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

            @Override
            public List creatArrayContainer() {
                return new LinkedList();
            }
        };


        lines.map(x ->{
            try{
                return (Map)parser.parse(x, containerFactory);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).forEach(consumer) ;
//        System.out.println(list);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Student> students = new ArrayList<>();
        list.forEach(stringStringMap -> students.add(objectMapper.convertValue(stringStringMap, Student.class)));

        System.out.println(students);
        System.out.println(students.get(0).getFirstName());
    }
}
