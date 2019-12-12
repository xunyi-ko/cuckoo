/**
 * 算法的枚举类，利用单例模式
 */
package site.xunyi.cuckoo.encryption;

/**
 * @author xunyi
 */
public enum Algorithms {
    SHA1(new SHA1()),
    HmacSHA1(new HmacSHA1());
    
    private Algorithm algorithm;
    Algorithms(Algorithm algorithm){
        this.algorithm = algorithm;
    }
    
    public String calc(String... strings) {
        return algorithm.calc(strings);
    }
}
