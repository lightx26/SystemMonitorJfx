package systemmonitor.Utilities;

import java.util.ArrayList;
import redis.clients.jedis.Jedis;

public class DataAccess {
    // private String key;

    Jedis jedis;
    static long lim = 100;

    public DataAccess() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushAll();
    }

    public ArrayList<Double> getCpuUsages(String clientName) {
        String key = "Client " + clientName + ":CPU";
        ArrayList<Double> list = new ArrayList<Double>();

        for (String s_cpu : jedis.lrange(key, 0, lim)) {
            list.add(Double.parseDouble(s_cpu));
        }

        return list;
    }

    public Double getCurrentCpuUsage(String clientName) {
        String key = "Client " + clientName + ":CPU";
        return Double.parseDouble(jedis.lindex(key, -1));
    }

    public void addCpuUsage(String clientName, Double cpu) {
        String key = "Client " + clientName + ":CPU";

        if (jedis.llen(key) >= lim)
            jedis.lpop(key);

        jedis.rpush(key, cpu.toString());
    }

    public ArrayList<Long> getMemoryUsages(String clientName) {
        String key = "Client " + clientName + ":Memory";
        ArrayList<Long> list = new ArrayList<Long>();

        for (String s_mem : jedis.lrange(key, 0, lim)) {
            list.add(Long.parseLong(s_mem));
        }

        return list;
    }

    public Long getCurrentMemoryUsage(String clientName) {
        String key = "Client " + clientName + ":Memory";
        return Long.parseLong(jedis.lindex(key, -1));
    }

    public void addMemUsage(String clientName, Long mem) {
        String key = "Client " + clientName + ":Memory";

        if (jedis.llen(key) >= lim)
            jedis.lpop(key);

        jedis.rpush(key, mem.toString());
    }

    public String getIP(String clientName) {
        String key = "Client " + clientName + ":IP";
        return jedis.get(key);
    }

    public void setIP(String clientName, String ip) {
        String key = "Client " + clientName + ":IP";
        jedis.set(key, ip);
    }

    public String getMAC(String clientName) {
        String key = "Client " + clientName + ":MAC";
        return jedis.get(key);
    }

    public void setMAC(String clientName, String mac) {
        String key = "Client " + clientName + ":MAC";
        jedis.set(key, mac);
    }

    public String getOSName(String clientName) {
        String key = "Client " + clientName + ":OS";
        return jedis.get(key);
    }

    public void setOSName(String clientName, String osname) {
        String key = "Client " + clientName + ":OS";
        jedis.set(key, osname);
    }

    public Long getTotalMem(String clientName) {
        String key = "Client " + clientName + ":TotalMem";
        return Long.parseLong(jedis.get(key));
    }

    public void setTotalMem(String clientName, Long totalMem) {
        String key = "Client " + clientName + ":TotalMem";
        jedis.set(key, Long.toString(totalMem));
    }

    public String getCPUModel(String clientName) {
        String key = "Client " + clientName + ":CPUModel";
        return jedis.get(key);
    }

    public void setCPUModel(String clientName, String cpumodel) {
        String key = "Client " + clientName + ":CPUModel";
        jedis.set(key, cpumodel);
    }

    public Long getTotalStorage(String clientName) {
        String key = "Client " + clientName + ":TotalStorage";
        return Long.parseLong(jedis.get(key));
    }

    public void setTotalStorage(String clientName, Long totalStorage) {
        String key = "Client " + clientName + ":TotalStorage";
        jedis.set(key, Long.toString(totalStorage));
    }

    public ArrayList<Double> getTrafficSend(String clientName) {
        String key = "Client " + clientName + ":TrafficSend";
        ArrayList<Double> list = new ArrayList<Double>();

        for (String s_send : jedis.lrange(key, 0, lim)) {
            list.add(Double.parseDouble(s_send));
        }

        return list;
    }

    public Double getCurrentTrafficSend(String clientName) {
        String key = "Client " + clientName + ":TrafficSend";
        return Double.parseDouble(jedis.lindex(key, -1));
    }

    public void addTrafficSend(String clientName, Double send) {
        String key = "Client " + clientName + ":TrafficSend";

        if (jedis.llen(key) >= lim)
            jedis.lpop(key);

        jedis.rpush(key, send.toString());
    }

    public ArrayList<Double> getTrafficReceived(String clientName) {
        String key = "Client " + clientName + ":TrafficReceived";
        ArrayList<Double> list = new ArrayList<Double>();

        for (String s_received : jedis.lrange(key, 0, lim)) {
            list.add(Double.parseDouble(s_received));
        }

        return list;
    }

    public Double getCurrentTrafficReceived(String clientName) {
        String key = "Client " + clientName + ":TrafficReceived";
        return Double.parseDouble(jedis.lindex(key, -1));
    }

    public void addTrafficReceived(String clientName, double received) {
        String key = "Client " + clientName + ":TrafficReceived";
        if (jedis.llen(key) >= lim)
            jedis.lpop(key);

        jedis.rpush(key, Double.toString(received));
    }

    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
