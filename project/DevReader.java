import java.util.Set;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;

 
  class DevReader {
        int vendor;
        int product;
        usbInfo u1 = new usbInfo();
        u1.showUSB();

        {
        Set<Map.Entry<Integer, Integer>> pairs = new HashSet<>();

        pairs.add(new AbstractMap.SimpleEntry<>(vendor, product));

        for (Map.Entry<Integer, Integer> pair : pairs) {
            System.out.println(pair.getKey() + ", " + pair.getValue());
        }
    }

}



