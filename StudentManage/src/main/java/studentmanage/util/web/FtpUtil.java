package studentmanage.util.web;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;

/**
 * ftp客户端
 */
public class FtpUtil {
    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 创建FTP客户端连接
     *
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @return
     */
    public static FTPClient createFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);
            ftpClient.login(ftpUserName, ftpPassword);
            ftpClient.setRemoteVerificationEnabled(false);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.error("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
                return null;
            }
            return ftpClient;
        } catch (SocketException e) {
            e.printStackTrace();
            logger.error("FTP的IP地址可能错误，请正确配置。", e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("FTP的端口错误,请正确配置。", e);
            return null;
        }
    }

    /**
     * 关闭ftp连接
     *
     * @param ftpClient
     * @throws IOException
     */
    public static void closeFtpClient(FTPClient ftpClient) throws IOException {
        if (ftpClient != null) {
            ftpClient.logout();
        }
    }

    /**
     * 下载ftp文件到本地目录
     *
     * @param ftpClient
     * @param ftpPath       ftp文件的文件夹路径（不包括文件名）
     * @param ftpFileName   ftp文件名
     * @param localPath     本地目录文件夹路径（不包括文件名）
     * @param localFileName 本地文件文件名
     */
    public static void downloadFtpFile(FTPClient ftpClient, String ftpPath, String ftpFileName, String localPath, String localFileName) {
        try {
            //切换ftp的当前目录
            ftpClient.changeWorkingDirectory(ftpPath);

            //判断文件是否存在，存在才下载
            if (ftpClient.listFiles(ftpFileName).length > 0) {
                File localFile = new File(localPath + File.separatorChar + localFileName);
                OutputStream os = new FileOutputStream(localFile);
                ftpClient.retrieveFile(ftpFileName, os);
                os.close();
            }
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件", e);
        } catch (SocketException e) {
            logger.error("连接FTP失败.", e);
        } catch (IOException e) {
            logger.error("文件读取错误。", e);
        }
    }

    /**
     * 删除ftp文件
     *
     * @param ftpClient
     * @param ftpPath
     * @param ftpFileName
     */
    public static void deleteFtpFile(FTPClient ftpClient, String ftpPath, String ftpFileName) {
        try {
            //切换ftp的当前目录
            ftpClient.changeWorkingDirectory(ftpPath);

            //删除文件
            ftpClient.deleteFile(ftpFileName);
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件", e);
        } catch (SocketException e) {
            logger.error("连接FTP失败.", e);
        } catch (IOException e) {
            logger.error("文件读取错误。", e);
        }
    }
}
