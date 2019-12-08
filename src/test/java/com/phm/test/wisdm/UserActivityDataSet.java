
package com.phm.test.wisdm;

/**
 *
 * @author phm
 */
public class UserActivityDataSet  {
//    public final HashMap<Integer, UserActivitiesInfo> users = new HashMap<>();
//
//    public Dataset<DataField<float []>> basedOnActivity (Activity act) {
//        Dataset<DataField<float []>> ds = new Dataset<>();
//        users.forEach((Integer uid, UserActivitiesInfo ua) -> {
//            UserActivitiesInfo.UserActivity uact = ua.activities.get(act);
//            if (uact != null) {
//                uact.data.stream().forEach((DataField<float []> tdf) -> {
//                    ds.add(new DataField<>(uact.activity.name, tdf.cdata()));
//                });
//            }
//        });
//        return ds;
//    }
//    public Dataset<DataField<float []>> basedOnActivity () {
//        Dataset<DataField<float []>> ds = new Dataset<>();
//        users.forEach((Integer uid, UserActivitiesInfo ua) -> {
//            ua.activities.forEach((Activity t, UserActivitiesInfo.UserActivity uact) -> {
//                uact.data.stream().forEach((DataField<float []> tdf) -> {
//                    ds.add(new DataField<>(uact.activity.name, tdf.cdata()));
//                });
//            });
//        });
//        return ds;
//    }
//    public Dataset<DataField<float []>> basedOnActivityAsMCHSeries (Activity act) {
//        Dataset<DataField<float []>> ds = new Dataset<>();
//        users.forEach((Integer uid, UserActivitiesInfo ua) -> {
//            UserActivitiesInfo.UserActivity uact = ua.activities.get(act);
//            if (uact != null) {
//                LinkedList<float []> list = new LinkedList<>();
//                float [] ax = new float[uact.data.size()];
//                float [] ay = new float[uact.data.size()];
//                float [] az = new float[uact.data.size()];
//                for (int index = 0; index < uact.data.size(); index++) {
//                    float [] tmp = uact.data.get(index).cdata(0);
//                    ax [index] = tmp [0];
//                    ay [index] = tmp [1];
//                    az [index] = tmp [2];
//                }
//                list.add(ax);
//                list.add(ay);
//                list.add(az);
//                DataField<float []> df = new DataField<>(uact.activity.name, list);
//                ds.add(df);
//            }
//        });
//        return ds;
//    }
//    public Dataset<DataField<float []>> basedOnActivitiesAsMCHSeries () {
//        Dataset<DataField<float []>> ds = new Dataset<>();
//        users.forEach((Integer uid, UserActivitiesInfo ua) -> {
//            ua.activities.forEach((Activity a, UserActivitiesInfo.UserActivity uact) -> {
//                LinkedList<float []> list = new LinkedList<>();
//                float [] ax = new float[uact.data.size()];
//                float [] ay = new float[uact.data.size()];
//                float [] az = new float[uact.data.size()];
//                for (int index = 0; index < uact.data.size(); index++) {
//                    float [] tmp = uact.data.get(index).cdata(0);
//                    ax [index] = tmp [0];
//                    ay [index] = tmp [1];
//                    az [index] = tmp [2];
//                }
//                list.add(ax);
//                list.add(ay);
//                list.add(az);
//                DataField<float []> df = new DataField<>(uact.activity.name, list);
//                ds.add(df);
//            });
//        });
//        return ds;
//    }
//    public boolean load (List<WISDMRecord> recs) {
//        if (recs == null || recs.size() < 1) return false;
//        recs.stream().forEach((WISDMRecord x) -> {
//            UserActivitiesInfo info = users.get(x.userid);
//            if (info == null) {
//                info = new UserActivitiesInfo();
//            }
//            info.userid = x.userid;
//            /////////////////////
//            UserActivitiesInfo.UserActivity ac = info.activities.get(x.activity);
//            if (ac == null) {
//                ac = new UserActivitiesInfo.UserActivity();
//            }
//            ac.activity = x.activity;
//            float [] data = new float [3];
//            data [0] = x.accX;
//            data [1] = x.accY;
//            data [2] = x.accZ;
//            LinkedList<float []> list = new LinkedList<>();
//            list.add(data);
//            DataField<float []> tmp = new DataField<float []>(x.timestamp, list);
//            ac.data.addLast(tmp);
//            info.activities.put(ac.activity, ac);
//            ////////////////////
//            users.put(info.userid, info);
//        });
//        
//        return true;
//    }
//    
//    public void normalize () {
//        // Remove User 0
//        users.remove(0);
//        // Sort all time series data;
////        users.forEach((Integer t, UserActivitiesInfo u) -> {
////            u.activities.forEach((Activity t1, UserActivitiesInfo.UserActivity x) -> {
////                
////            });
////        });
//    }
}
