
package com.phm.test.opportunity;

/**
 *
 * @author phm
 */
public class OpportunityDSLoader {
//    
//    public static LinkedList<OpportunityDSRecord> loadAsList (File dspath) throws FileNotFoundException, IOException {
//        LinkedList<OpportunityDSRecord> res = new LinkedList<>();
//        if (dspath == null) return null;
//        try (BufferedReader brin = new BufferedReader(new FileReader(dspath))) {
//            brin.lines().forEach((String x) -> {
//                String [] flds = x.split(" ");
//                OpportunityDSRecord odsr = loadOpportunityRecord(x);
//                res.add(odsr);
//            });
//        }
//        
//        return res;
//    }
//    
//    public static Dataset<DataField<float []>> normalizeActivityDS (Dataset<DataField<float []>> ds) {
//        Dataset<DataField<float []>> tmp = new Dataset<>(ds);
//        for (DataField<float []> x : tmp) {
//            if (x.label.contentEquals(OpportunityDSRecord.Locomotion.Null.name())) {
//                ds.remove (x);
//            }
//        }
//        return ds;
//    }
//    
//    public static Dataset<DataField<float []>> loadAsActivityDS (File dspath) throws IOException {
//        List<OpportunityDSRecord> list = loadAsList(dspath);
//        Dataset<DataField<float []>> res = new Dataset<>();
//        LinkedList<OpportunityDSRecord> ltmp = new LinkedList<>();
//        OpportunityDSRecord dftmp = list.get(0);
//        for (int index = 0; index < list.size(); index++) {
//            OpportunityDSRecord tmp = list.get(index);
//            if (dftmp.locomotion != tmp.locomotion) {
//                dftmp = tmp;
//                if (ltmp.size() > 1) {
//                    DataField<float []> df = new DataField<>(ltmp.get(0).locomotion.name(), 15);
//                    float [] accelerometer_RKN_accX = new float [ltmp.size()];
//                    float [] accelerometer_RKN_accY = new float [ltmp.size()];
//                    float [] accelerometer_RKN_accZ = new float [ltmp.size()];
//                    float [] accelerometer_HIP_accX = new float [ltmp.size()];
//                    float [] accelerometer_HIP_accY = new float [ltmp.size()];
//                    float [] accelerometer_HIP_accZ = new float [ltmp.size()];
//                    float [] accelerometer_LUA_accX = new float [ltmp.size()];
//                    float [] accelerometer_LUA_accY = new float [ltmp.size()];
//                    float [] accelerometer_LUA_accZ = new float [ltmp.size()];
//                    float [] accelerometer__RUA_accX = new float [ltmp.size()];
//                    float [] accelerometer__RUA_accY = new float [ltmp.size()];
//                    float [] accelerometer__RUA_accZ = new float [ltmp.size()];
//                    float [] accelerometer_LH_accX = new float [ltmp.size()];
//                    float [] accelerometer_LH_accY = new float [ltmp.size()];
//                    float [] accelerometer_LH_accZ = new float [ltmp.size()];
//                    
//                    for (int dim = 0; dim < ltmp.size(); dim++) {
//                        OpportunityDSRecord dimtmp = ltmp.get(dim);
//                        accelerometer_RKN_accX [dim] = Float.isNaN(dimtmp.accelerometer_RKN_accX) ? 0 : dimtmp.accelerometer_RKN_accX;
//                        accelerometer_RKN_accY [dim] = Float.isNaN(dimtmp.accelerometer_RKN_accY) ? 0 : dimtmp.accelerometer_RKN_accY;
//                        accelerometer_RKN_accZ [dim] = Float.isNaN(dimtmp.accelerometer_RKN_accZ) ? 0 : dimtmp.accelerometer_RKN_accZ;
//                        accelerometer_HIP_accX [dim] = Float.isNaN(dimtmp.accelerometer_HIP_accX) ? 0 : dimtmp.accelerometer_HIP_accX;
//                        accelerometer_HIP_accY [dim] = Float.isNaN(dimtmp.accelerometer_HIP_accY) ? 0 : dimtmp.accelerometer_HIP_accY;
//                        accelerometer_HIP_accZ [dim] = Float.isNaN(dimtmp.accelerometer_HIP_accZ) ? 0 : dimtmp.accelerometer_HIP_accZ;
//                        accelerometer_LUA_accX [dim] = Float.isNaN(dimtmp.accelerometer_LUA_accX) ? 0 : dimtmp.accelerometer_LUA_accX;
//                        accelerometer_LUA_accY [dim] = Float.isNaN(dimtmp.accelerometer_LUA_accY) ? 0 : dimtmp.accelerometer_LUA_accY;
//                        accelerometer_LUA_accZ [dim] = Float.isNaN(dimtmp.accelerometer_LUA_accZ) ? 0 : dimtmp.accelerometer_LUA_accZ;
//                        accelerometer__RUA_accX [dim] = Float.isNaN(dimtmp.accelerometer__RUA_accX) ? 0 : dimtmp.accelerometer__RUA_accX;
//                        accelerometer__RUA_accY [dim] = Float.isNaN(dimtmp.accelerometer__RUA_accY) ? 0 : dimtmp.accelerometer__RUA_accY;
//                        accelerometer__RUA_accZ [dim] = Float.isNaN(dimtmp.accelerometer__RUA_accZ) ? 0 : dimtmp.accelerometer__RUA_accZ;
//                        accelerometer_LH_accX [dim] = Float.isNaN(dimtmp.accelerometer_LH_accX) ? 0 : dimtmp.accelerometer_LH_accX;
//                        accelerometer_LH_accY [dim] = Float.isNaN(dimtmp.accelerometer_LH_accY) ? 0 : dimtmp.accelerometer_LH_accY;
//                        accelerometer_LH_accZ [dim] = Float.isNaN(dimtmp.accelerometer_LH_accZ) ? 0 : dimtmp.accelerometer_LH_accZ;
//                    }
//                    
//                    df.setChannel(0, accelerometer_RKN_accX);
//                    df.setChannel(1, accelerometer_RKN_accY);
//                    df.setChannel(2, accelerometer_RKN_accZ);
//                    df.setChannel(3, accelerometer_HIP_accX);
//                    df.setChannel(4, accelerometer_HIP_accY);
//                    df.setChannel(5, accelerometer_HIP_accZ);
//                    df.setChannel(6, accelerometer_LUA_accX);
//                    df.setChannel(7, accelerometer_LUA_accY);
//                    df.setChannel(8, accelerometer_LUA_accZ);
//                    df.setChannel(9, accelerometer__RUA_accX);
//                    df.setChannel(10, accelerometer__RUA_accY);
//                    df.setChannel(11, accelerometer__RUA_accZ);
//                    df.setChannel(12, accelerometer_LH_accX);
//                    df.setChannel(13, accelerometer_LH_accY);
//                    df.setChannel(14, accelerometer_LH_accZ);
//                    res.add(df);
//                }
//                ltmp = new LinkedList<>();
//            }
//            ltmp.add(tmp);
//        }
//        return res;
//    }
//    
//    private static OpportunityDSRecord loadOpportunityRecord (String fld) {
//        String [] flds = fld.split(" ");
//        OpportunityDSRecord op = new OpportunityDSRecord ();
//        op.timestamp = Integer.valueOf (flds [OpportunityDSRecord.INDEX_timestamp]);
//        op.accelerometer_BACK_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BACK_accX]);
//        op.accelerometer_BACK_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BACK_accY]);
//        op.accelerometer_BACK_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BACK_accZ]);
//        op.accelerometer_BREAD_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BREAD_accX]);
//        op.accelerometer_BREAD_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BREAD_accX2]);
//        op.accelerometer_BREAD_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BREAD_accX3]);
//        op.accelerometer_BREAD_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_BREAD_gyroX]);
//        op.accelerometer_BREAD_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_gyroY]);
//        op.accelerometer_CHEESE_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_accX]);
//        op.accelerometer_CHEESE_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_accX2]);
//        op.accelerometer_CHEESE_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_accX3]);
//        op.accelerometer_CHEESE_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_gyroX]);
//        op.accelerometer_CHEESE_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CHEESE_gyroY]);
//        op.accelerometer_CUP_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CUP_accX]);
//        op.accelerometer_CUP_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CUP_accX2]);
//        op.accelerometer_CUP_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CUP_accX3]);
//        op.accelerometer_CUP_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CUP_gyroX]);
//        op.accelerometer_CUP_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_CUP_gyroY]);
//        op.accelerometer_DISHWASHER_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DISHWASHER_accX]);
//        op.accelerometer_DISHWASHER_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DISHWASHER_accY]);
//        op.accelerometer_DISHWASHER_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DISHWASHER_accZ]);
//        op.accelerometer_DOOR1_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR1_accX]);
//        op.accelerometer_DOOR1_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR1_accY]);
//        op.accelerometer_DOOR1_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR1_accZ]);
//        op.accelerometer_DOOR2_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR2_accX]);
//        op.accelerometer_DOOR2_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR2_accY]);
//        op.accelerometer_DOOR2_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_DOOR2_accZ]);
//        op.accelerometer_FRIDGE_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_FRIDGE_accX]);
//        op.accelerometer_FRIDGE_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_FRIDGE_accY]);
//        op.accelerometer_FRIDGE_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_FRIDGE_accZ]);
//        op.accelerometer_GLASS_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_GLASS_accX]);
//        op.accelerometer_GLASS_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_GLASS_accX2]);
//        op.accelerometer_GLASS_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_GLASS_accX3]);
//        op.accelerometer_GLASS_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_GLASS_gyroX]);
//        op.accelerometer_GLASS_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_GLASS_gyroY]);
//        op.accelerometer_HIP_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_HIP_accX]);
//        op.accelerometer_HIP_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_HIP_accY]);
//        op.accelerometer_HIP_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_HIP_accZ]);
//        op.accelerometer_KNIFE1_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE1_accX]);
//        op.accelerometer_KNIFE1_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE1_accX2]);
//        op.accelerometer_KNIFE1_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE1_accX3]);
//        op.accelerometer_KNIFE1_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE1_gyroX]);
//        op.accelerometer_KNIFE1_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE1_gyroY]);
//        op.accelerometer_KNIFE2_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE2_accX]);
//        op.accelerometer_KNIFE2_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE2_accX2]);
//        op.accelerometer_KNIFE2_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE2_accX3]);
//        op.accelerometer_KNIFE2_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE2_gyroX]);
//        op.accelerometer_KNIFE2_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_KNIFE2_gyroY]);
//        op.accelerometer_LAZYCHAIR_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LAZYCHAIR_accX]);
//        op.accelerometer_LAZYCHAIR_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LAZYCHAIR_accY]);
//        op.accelerometer_LAZYCHAIR_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LAZYCHAIR_accZ]);
//        op.accelerometer_LH_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LH_accX]);
//        op.accelerometer_LH_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LH_accY]);
//        op.accelerometer_LH_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LH_accZ]);
//        op.accelerometer_LOWERDRAWER_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LOWERDRAWER_accX]);
//        op.accelerometer_LOWERDRAWER_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LOWERDRAWER_accY]);
//        op.accelerometer_LOWERDRAWER_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LOWERDRAWER_accZ]);
//        op.accelerometer_LUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LUA_accX]);
//        op.accelerometer_LUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LUA_accY]);
//        op.accelerometer_LUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LUA_accZ]);
//        op.accelerometer_LWR_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LWR_accX]);
//        op.accelerometer_LWR_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LWR_accY]);
//        op.accelerometer_LWR_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_LWR_accZ]);
//        op.accelerometer_MIDDLEDRAWER_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MIDDLEDRAWER_accX]);
//        op.accelerometer_MIDDLEDRAWER_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MIDDLEDRAWER_accY]);
//        op.accelerometer_MIDDLEDRAWER_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MIDDLEDRAWER_accZ]);
//        op.accelerometer_MILK_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MILK_accX]);
//        op.accelerometer_MILK_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MILK_accX2]);
//        op.accelerometer_MILK_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MILK_accX3]);
//        op.accelerometer_MILK_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MILK_gyroX]);
//        op.accelerometer_MILK_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_MILK_gyroY]);
//        op.accelerometer_PLATE_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_PLATE_accX]);
//        op.accelerometer_PLATE_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_PLATE_accX2]);
//        op.accelerometer_PLATE_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_PLATE_accX3]);
//        op.accelerometer_PLATE_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_PLATE_gyroX]);
//        op.accelerometer_PLATE_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_PLATE_gyroY]);
//        op.accelerometer_RH_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RH_accX]);
//        op.accelerometer_RH_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RH_accY]);
//        op.accelerometer_RH_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RH_accZ]);
//        op.accelerometer_RKN_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RKN_accX]);
//        op.accelerometer_RKN_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RKN_accY]);
//        op.accelerometer_RKN_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RKN_accZ]);
//        op.accelerometer_RUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RUA_accX]);
//        op.accelerometer_RUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RUA_accY]);
//        op.accelerometer_RUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RUA_accZ]);
//        op.accelerometer_RWR_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RWR_accX]);
//        op.accelerometer_RWR_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RWR_accY]);
//        op.accelerometer_RWR_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_RWR_accZ]);
//        op.accelerometer_SALAMI_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SALAMI_accX]);
//        op.accelerometer_SALAMI_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SALAMI_accX2]);
//        op.accelerometer_SALAMI_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SALAMI_accX3]);
//        op.accelerometer_SALAMI_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SALAMI_gyroX]);
//        op.accelerometer_SALAMI_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SALAMI_gyroY]);
//        op.accelerometer_SPOON_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SPOON_accX]);
//        op.accelerometer_SPOON_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SPOON_accX2]);
//        op.accelerometer_SPOON_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SPOON_accX3]);
//        op.accelerometer_SPOON_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SPOON_gyroX]);
//        op.accelerometer_SPOON_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SPOON_gyroY]);
//        op.accelerometer_SUGAR_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SUGAR_accX]);
//        op.accelerometer_SUGAR_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SUGAR_accX2]);
//        op.accelerometer_SUGAR_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SUGAR_accX3]);
//        op.accelerometer_SUGAR_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SUGAR_gyroX]);
//        op.accelerometer_SUGAR_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_SUGAR_gyroY]);
//        op.accelerometer_UPPERDRAWER_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_UPPERDRAWER_accX]);
//        op.accelerometer_UPPERDRAWER_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_UPPERDRAWER_accY]);
//        op.accelerometer_UPPERDRAWER_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_UPPERDRAWER_accZ]);
//        op.accelerometer_WATER_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_WATER_accX]);
//        op.accelerometer_WATER_accX2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_WATER_accX2]);
//        op.accelerometer_WATER_accX3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_WATER_accX3]);
//        op.accelerometer_WATER_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_WATER_gyroX]);
//        op.accelerometer_WATER_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer_WATER_gyroY]);
//        op.accelerometer__LUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__LUA_accX]);
//        op.accelerometer__LUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__LUA_accY]);
//        op.accelerometer__LUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__LUA_accZ]);
//        op.accelerometer__RKN_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RKN_accX]);
//        op.accelerometer__RKN_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RKN_accY]);
//        op.accelerometer__RKN_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RKN_accZ]);
//        op.accelerometer__RUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RUA_accX]);
//        op.accelerometer__RUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RUA_accY]);
//        op.accelerometer__RUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_accelerometer__RUA_accZ]);
//        op.imu_BACK_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_accX]);
//        op.imu_BACK_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_accY]);
//        op.imu_BACK_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_accZ]);
//        op.imu_BACK_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_gyroX]);
//        op.imu_BACK_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_gyroY]);
//        op.imu_BACK_gyroZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_gyroZ]);
//        op.imu_BACK_magnetX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_magnetX]);
//        op.imu_BACK_magnetY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_magnetY]);
//        op.imu_BACK_magnetZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_magnetZ]);
//        op.imu_BACK_quater1 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_quater1]);
//        op.imu_BACK_quater2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_quater2]);
//        op.imu_BACK_quater3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_quater3]);
//        op.imu_BACK_quater4 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_BACK_quater4]);
//        op.imu_LLA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_accX]);
//        op.imu_LLA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_accY]);
//        op.imu_LLA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_accZ]);
//        op.imu_LLA_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_gyroX]);
//        op.imu_LLA_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_gyroY]);
//        op.imu_LLA_gyroZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_gyroZ]);
//        op.imu_LLA_magnetX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_magnetX]);
//        op.imu_LLA_magnetY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_magnetY]);
//        op.imu_LLA_magnetZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_magnetZ]);
//        op.imu_LLA_quater1 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_quater1]);
//        op.imu_LLA_quater2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_quater2]);
//        op.imu_LLA_quater3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_quater3]);
//        op.imu_LLA_quater4 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LLA_quater4]);
//        op.imu_LSHOE_AngVelBodyFrameX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelBodyFrameX]);
//        op.imu_LSHOE_AngVelBodyFrameY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelBodyFrameY]);
//        op.imu_LSHOE_AngVelBodyFrameZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelBodyFrameZ]);
//        op.imu_LSHOE_AngVelNavFrameX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelNavFrameX]);
//        op.imu_LSHOE_AngVelNavFrameY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelNavFrameY]);
//        op.imu_LSHOE_AngVelNavFrameZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_AngVelNavFrameZ]);
//        op.imu_LSHOE_Body_Ax = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Body_Ax]);
//        op.imu_LSHOE_Body_Ay = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Body_Ay]);
//        op.imu_LSHOE_Body_Az = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Body_Az]);
//        op.imu_LSHOE_Compass = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Compass]);
//        op.imu_LSHOE_EuX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_EuX]);
//        op.imu_LSHOE_EuY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_EuY]);
//        op.imu_LSHOE_EuZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_EuZ]);
//        op.imu_LSHOE_Nav_Ax = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Nav_Ax]);
//        op.imu_LSHOE_Nav_Ay = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Nav_Ay]);
//        op.imu_LSHOE_Nav_Az = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LSHOE_Nav_Az]);
//        op.imu_LUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_accX]);
//        op.imu_LUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_accY]);
//        op.imu_LUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_accZ]);
//        op.imu_LUA_magnetX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_magnetX]);
//        op.imu_LUA_magnetY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_magnetY]);
//        op.imu_LUA_magnetZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_magnetZ]);
//        op.imu_LUA_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_gyroX]);
//        op.imu_LUA_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_gyroY]);
//        op.imu_LUA_gyroZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_gyroZ]);
//        op.imu_LUA_quater1 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_quater1]);
//        op.imu_LUA_quater2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_quater2]);
//        op.imu_LUA_quater3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_quater3]);
//        op.imu_LUA_quater4 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_LUA_quater4]);
//        op.imu_RLA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_accX]);
//        op.imu_RLA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_accY]);
//        op.imu_RLA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_accZ]);
//        op.imu_RLA_magnetX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_magnetX]);
//        op.imu_RLA_magnetY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_magnetY]);
//        op.imu_RLA_magnetZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_magnetZ]);
//        op.imu_RLA_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_gyroX]);
//        op.imu_RLA_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_gyroY]);
//        op.imu_RLA_gyroZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_gyroZ]);
//        op.imu_RLA_quater1 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_quater1]);
//        op.imu_RLA_quater2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_quater2]);
//        op.imu_RLA_quater3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_quater3]);
//        op.imu_RLA_quater4 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RLA_quater4]);
//        op.imu_RSHOE_AngVelBodyFrameX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelBodyFrameX]);
//        op.imu_RSHOE_AngVelBodyFrameY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelBodyFrameY]);
//        op.imu_RSHOE_AngVelBodyFrameZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelBodyFrameZ]);
//        op.imu_RSHOE_AngVelNavFrameX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelNavFrameX]);
//        op.imu_RSHOE_AngVelNavFrameY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelNavFrameY]);
//        op.imu_RSHOE_AngVelNavFrameZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_AngVelNavFrameZ]);
//        op.imu_RSHOE_Body_Ax = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Body_Ax]);
//        op.imu_RSHOE_Body_Ay = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Body_Ay]);
//        op.imu_RSHOE_Body_Az = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Body_Az]);
//        op.imu_RSHOE_Compass = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Compass]);
//        op.imu_RSHOE_EuX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_EuX]);
//        op.imu_RSHOE_EuY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_EuY]);
//        op.imu_RSHOE_EuZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_EuZ]);
//        op.imu_RSHOE_Nav_Ax = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Nav_Ax]);
//        op.imu_RSHOE_Nav_Ay = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Nav_Ay]);
//        op.imu_RSHOE_Nav_Az = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RSHOE_Nav_Az]);
//        op.imu_RUA_accX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_accX]);
//        op.imu_RUA_accY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_accY]);
//        op.imu_RUA_accZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_accZ]);
//        op.imu_RUA_magnetX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_magnetX]);
//        op.imu_RUA_magnetY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_magnetY]);
//        op.imu_RUA_magnetZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_magnetZ]);
//        op.imu_RUA_gyroX = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_gyroX]);
//        op.imu_RUA_gyroY = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_gyroY]);
//        op.imu_RUA_gyroZ = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_gyroZ]);
//        op.imu_RUA_quater1 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_quater1]);
//        op.imu_RUA_quater2 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_quater2]);
//        op.imu_RUA_quater3 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_quater3]);
//        op.imu_RUA_quater4 = Float.valueOf (flds [OpportunityDSRecord.INDEX_imu_RUA_quater4]);
//        op.location_tag1_X = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag1_X]);
//        op.location_tag1_Y = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag1_Y]);
//        op.location_tag1_Z = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag1_Z]);
//        op.location_tag2_X = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag2_X]);
//        op.location_tag2_Y = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag2_Y]);
//        op.location_tag2_Z = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag2_Z]);
//        op.location_tag3_X = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag3_X]);
//        op.location_tag3_Y = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag3_Y]);
//        op.location_tag3_Z = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag3_Z]);
//        op.location_tag4_X = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag4_X]);
//        op.location_tag4_Y = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag4_Y]);
//        op.location_tag4_Z = Float.valueOf (flds [OpportunityDSRecord.INDEX_location_tag4_Z]);
//        op.reed_switch_dishwasher_s1 = !flds [OpportunityDSRecord.INDEX_reed_switch_dishwasher_s1].contentEquals("0");
//        op.reed_switch_dishwasher_s2 = !flds [OpportunityDSRecord.INDEX_reed_switch_dishwasher_s2].contentEquals("0");
//        op.reed_switch_dishwasher_s3 = !flds [OpportunityDSRecord.INDEX_reed_switch_dishwasher_s3].contentEquals("0");
//        op.reed_switch_fridge_s1 = !flds [OpportunityDSRecord.INDEX_reed_switch_fridge_s1].contentEquals("0");
//        op.reed_switch_fridge_s2 = !flds [OpportunityDSRecord.INDEX_reed_switch_fridge_s2].contentEquals("0");
//        op.reed_switch_fridge_s3 = !flds [OpportunityDSRecord.INDEX_reed_switch_fridge_s3].contentEquals("0");
//        op.reed_switch_lowerdrawer_s1 = !flds [OpportunityDSRecord.INDEX_reed_switch_lowerdrawer_s1].contentEquals("0");
//        op.reed_switch_lowerdrawer_s2 = !flds [OpportunityDSRecord.INDEX_reed_switch_lowerdrawer_s2].contentEquals("0");
//        op.reed_switch_lowerdrawer_s3 = !flds [OpportunityDSRecord.INDEX_reed_switch_lowerdrawer_s3].contentEquals("0");
//        op.reed_switch_middledrawer_s1 = !flds [OpportunityDSRecord.INDEX_reed_switch_middledrawer_s1].contentEquals("0");
//        op.reed_switch_middledrawer_s2 = !flds [OpportunityDSRecord.INDEX_reed_switch_middledrawer_s2].contentEquals("0");
//        op.reed_switch_middledrawer_s3 = !flds [OpportunityDSRecord.INDEX_reed_switch_middledrawer_s3].contentEquals("0");
//        op.reed_switch_upperdrawer_s3 = !flds [OpportunityDSRecord.INDEX_reed_switch_upperdrawer_s3].contentEquals("0");
//        String temp = flds [OpportunityDSRecord.INDEX_hlActivity];
//        System.out.print (temp + "\t");
//        op.hlActivity = OpportunityDSRecord.HL_Activity.value(temp);
//        temp = flds [OpportunityDSRecord.INDEX_llLeftArm];
//        System.out.print (temp + "\t");
//        op.llLeftArm = OpportunityDSRecord.LL_Left_Arm.value(temp);
//        temp = flds [OpportunityDSRecord.INDEX_llLeftArmObject];
//        System.out.print (temp + "\t");
//        op.llLeftArmObject = OpportunityDSRecord.LL_Left_Arm_Object.value (temp);
//        temp = flds [OpportunityDSRecord.INDEX_llRightArm];
//        System.out.print (temp + "\t");
//        op.llRightArm = OpportunityDSRecord.LL_Right_Arm.value(temp);
//        temp = flds [OpportunityDSRecord.INDEX_llRightArmObject];
//        System.out.print (temp + "\t");
//        op.llRightArmObject = OpportunityDSRecord.LL_Right_Arm_Object.value(temp);
//        temp = flds [OpportunityDSRecord.INDEX_mlBothArms];
//        System.out.print (temp + "\t");
//        op.mlBothArms = OpportunityDSRecord.ML_Both_Arms.value(temp);
//        temp = flds [OpportunityDSRecord.INDEX_locomotion];
//        System.out.println (temp);
//        op.locomotion = OpportunityDSRecord.Locomotion.value(temp);
//        return op;
//    }
}
