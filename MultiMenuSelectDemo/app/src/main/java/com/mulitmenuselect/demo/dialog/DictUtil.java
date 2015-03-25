package com.mulitmenuselect.demo.dialog;

import android.content.Context;
import android.content.res.AssetManager;

import com.mulitmenuselect.demo.dialog.DictUnit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DictUtil {
	public static List<DictUnit> city1, city2, city3, industry, position, major, stuMajor, practice_salary2, partTimeJob;
	public static HashMap<Integer, ArrayList<DictUnit>> citys1, citys2, citys3, industrys, positions,
	majors, stuMajors, practice_salary2s, partTimeJobs;


    public static List<DictUnit> getPositions(Context context, int flag, String fileName) {
        if (position == null) {
            initPositions(context, fileName);
        }
        if (flag == 0) {
            return position;
        } else {
            return positions.get(flag);
        }
    }

    private static void initPositions(Context context, String fileName) {
        String industryString = readAssetsTXT(context, fileName);
        String[] strings = industryString.split(";");
        position = new ArrayList<DictUnit>();
        positions = new HashMap<Integer, ArrayList<DictUnit>>();
        for (int i = 0; i < strings.length; i++) {
            String[] items = strings[i].split(",");
            DictUnit tmp = new DictUnit();
            tmp.id = Integer.parseInt(items[0].trim());
            tmp.name = items[1];
            tmp.flag = Integer.parseInt(items[2].trim());
            if (tmp.flag == 0) {
                position.add(tmp);
            } else {
                if (positions.get(tmp.flag) == null) {
                    ArrayList<DictUnit> temp = new ArrayList<DictUnit>();
                    temp.add(tmp);
                    positions.put(tmp.flag, temp);
                } else {
                    positions.get(tmp.flag).add(tmp);
                }
            }
        }
    }

    /**
     * 读取assets文件夹下的txt字典库文�?
     *
     * @param context
     *            句柄
     * @param fName
     *            文件�?
     * @return
     */
    public static String readAssetsTXT(Context context, String fName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fName);
            byte[] bytes = new byte[1024];
            int leng;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((leng = is.read(bytes)) != -1) {
                baos.write(bytes, 0, leng);
            }
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
