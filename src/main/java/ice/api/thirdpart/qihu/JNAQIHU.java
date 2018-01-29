package ice.api.thirdpart.qihu;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.apache.log4j.Logger;


public class JNAQIHU {
    private final static Logger logger = Logger.getLogger(JNAQIHU.class);
    private QIHULibrary lib = QIHULibrary.INSTANCE;
    private Pointer pointer;

    final byte[] key =
        {(byte) 0xf4, (byte) 0x9e, 0x75, 0x2a, 0x4e, 0x0c, 0x6c, (byte) 0x92, 0x78, 0x1d, 0x06, (byte) 0xfe, 0x50,
            (byte) 0xe8, 0x69, (byte) 0xf2, (byte) 0xb2, (byte) 0xcf, 0x5d, 0x50, (byte) 0xca, 0x45, (byte) 0xe2, 0x22,
            (byte) 0xc4, 0x5d, 0x18, (byte) 0xc8, 0x05, 0x16, 0x06, 0x1d};

    public JNAQIHU() {
        pointer = lib.qhsec_create_handler();

        boolean b = lib.qhsec_add_public_key(pointer, 1, key, getQihuNonce());
        if (!b) {
            logger.error("qihusec library init fail,error code is :" + lib.qhsec_error_code(pointer));
        } else
            lib.qhsec_set_net_method(pointer, 0);
    }

    private String getQihuNonce() {
        NativeLibrary c = NativeLibrary.getInstance("qhsec");
        Pointer p = c.getGlobalVariableAddress("qhsec_nonce");
        Pointer pp = p.getPointer(0);
        return pp.getString(0);
    }

    /**
     * 加密
     */
    public byte[] encryption(String plain) throws Exception {

        int cipherLen = lib.qhsec_request_bound(plain.length());
        byte[] cipherBuf = new byte[cipherLen];
        IntByReference cipherLenRefernce = new IntByReference(cipherLen);
        boolean rb =
            lib.qhsec_build_request(pointer, plain.getBytes(), plain.getBytes().length, cipherBuf, cipherLenRefernce);
        if (!rb) {
            throw new Exception("encryption fail,error code is:" + this.lib.qhsec_error_code(this.pointer));
        }
        int finalCipherLen = cipherLenRefernce.getPointer().getInt(0);
        byte[] cipher = new byte[finalCipherLen];

        System.arraycopy(cipherBuf, 0, cipher, 0, finalCipherLen);

        return cipher;
    }

    /***
     * 解密
     */
    public byte[] decryption(byte[] cipher) throws Exception {
        int finalCipherLen = cipher.length;
        int responsePlainLen = lib.qhsec_response_bound(finalCipherLen);
        byte[] responsePlainBytes = new byte[responsePlainLen];
        IntByReference plainLenByReFerence = new IntByReference(responsePlainLen);

        boolean rp = lib.qhsec_parse_response(pointer, cipher, finalCipherLen, responsePlainBytes, plainLenByReFerence);

        if (!rp) {
            throw new Exception("decryption fail,error code is:" + this.lib.qhsec_error_code(this.pointer));
        }

        int finalPlainLen = plainLenByReFerence.getPointer().getInt(0);
        byte plains[] = new byte[finalPlainLen];
        System.arraycopy(responsePlainBytes, 0, plains, 0, finalPlainLen);

        return plains;
    }


    public interface QIHULibrary extends Library {
        String nonce = "316b91494d610d5492487f01";
        int VXERR_OK = 0;
        int VXERR_INVAL_ARGS = -1;
        //	      int VXERR_ENC_REQUEST = -2;	// 加密上行包出错
        int VXERR_DEC_RESPONSE = -3;  // 解析返回报文出错
        int VXERR_OUTOFMEMORY = -4;
        int VXERR_NO_SYMKEY = -5;  // 没有对称密钥，需要先执行V6协商
        int VXERR_SYMKEY_EXPIRED = -6;  // 对称密钥过期，需要重新用V6协商
        int VXERR_CANCELED = -7;  // 用户取消
        int VXERR_NETQUERY = -8;  // 网络查询失败
        int VXERR_ENC_REQUEST_ASYMM = -9;  // 加密上行包出错, 非对称加密失败，有可能是内存大小不够
        int VXERR_ENC_REQUEST_SYMM = -10;  // 加密上行包出错, 对称加密失败，有可能是内存大小不够
        int VXERR_DEC_DATA_FORMAT = -11; // 返回报文格式错误

        QIHULibrary INSTANCE = (QIHULibrary) Native.loadLibrary("qhsec", QIHULibrary.class);

        Pointer qhsec_create_handler();

        void qhsec_destroy_handler(Pointer pointer);

        void qhsec_set_net_method(Pointer pointer, int netMethod);

        boolean qhsec_add_public_key(Pointer pointer, int publicKeyId, byte[] cipherBuf, String nonce);

        int qhsec_request_bound(int plainLen);

        boolean qhsec_build_request(Pointer pointer, byte[] plain, int plainLength, byte[] cipherBuf,
            IntByReference cipherBufLen);

        int qhsec_response_bound(int cipherLen);

        boolean qhsec_parse_response(Pointer pointer, byte[] cipher, int cipherLen, byte[] plainBuf,
            IntByReference plainBufLen);

        int qhsec_error_code(Pointer pointer);
    }
}
