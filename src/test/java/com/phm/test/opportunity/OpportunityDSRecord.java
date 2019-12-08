
package com.phm.test.opportunity;

import java.io.Serializable;

/**
 *
 * @author phm
 */
public class OpportunityDSRecord implements Serializable {
    
    public static int INDEX_timestamp = 0;
    public static int INDEX_accelerometer_RKN_accX = 1; 
    public static int INDEX_accelerometer_RKN_accY = 2;
    public static int INDEX_accelerometer_RKN_accZ = 3;
    public static int INDEX_accelerometer_HIP_accX = 4; 
    public static int INDEX_accelerometer_HIP_accY = 5;
    public static int INDEX_accelerometer_HIP_accZ = 6;
    public static int INDEX_accelerometer_LUA_accX = 7; 
    public static int INDEX_accelerometer_LUA_accY = 8;
    public static int INDEX_accelerometer_LUA_accZ = 9;
    public static int INDEX_accelerometer__RUA_accX = 10; 
    public static int INDEX_accelerometer__RUA_accY = 11;
    public static int INDEX_accelerometer__RUA_accZ = 12;
    public static int INDEX_accelerometer_LH_accX = 13; 
    public static int INDEX_accelerometer_LH_accY = 14;
    public static int INDEX_accelerometer_LH_accZ = 15;
    public static int INDEX_accelerometer_BACK_accX = 16; 
    public static int INDEX_accelerometer_BACK_accY = 17;
    public static int INDEX_accelerometer_BACK_accZ = 18;
    public static int INDEX_accelerometer__RKN_accX = 19; 
    public static int INDEX_accelerometer__RKN_accY = 20;
    public static int INDEX_accelerometer__RKN_accZ = 21;
    public static int INDEX_accelerometer_RWR_accX = 22; 
    public static int INDEX_accelerometer_RWR_accY = 23;
    public static int INDEX_accelerometer_RWR_accZ = 24;
    public static int INDEX_accelerometer_RUA_accX = 25; 
    public static int INDEX_accelerometer_RUA_accY = 26;
    public static int INDEX_accelerometer_RUA_accZ = 27;
    public static int INDEX_accelerometer__LUA_accX = 28; 
    public static int INDEX_accelerometer__LUA_accY = 29;
    public static int INDEX_accelerometer__LUA_accZ = 30;
    public static int INDEX_accelerometer_LWR_accX = 31; 
    public static int INDEX_accelerometer_LWR_accY = 32;
    public static int INDEX_accelerometer_LWR_accZ = 33;
    public static int INDEX_accelerometer_RH_accX = 34; 
    public static int INDEX_accelerometer_RH_accY = 35;
    public static int INDEX_accelerometer_RH_accZ = 36;
    public static int INDEX_imu_BACK_accX = 37;
    public static int INDEX_imu_BACK_accY = 38;
    public static int INDEX_imu_BACK_accZ = 39;
    public static int INDEX_imu_BACK_gyroX = 40;
    public static int INDEX_imu_BACK_gyroY = 41;
    public static int INDEX_imu_BACK_gyroZ = 42;
    public static int INDEX_imu_BACK_magnetX = 43;
    public static int INDEX_imu_BACK_magnetY = 44;
    public static int INDEX_imu_BACK_magnetZ = 45;
    public static int INDEX_imu_BACK_quater1 = 46;
    public static int INDEX_imu_BACK_quater2 = 47;
    public static int INDEX_imu_BACK_quater3 = 48;
    public static int INDEX_imu_BACK_quater4 = 49;
    public static int INDEX_imu_RUA_accX = 50;
    public static int INDEX_imu_RUA_accY = 51;
    public static int INDEX_imu_RUA_accZ = 52;
    public static int INDEX_imu_RUA_gyroX = 53;
    public static int INDEX_imu_RUA_gyroY = 54;
    public static int INDEX_imu_RUA_gyroZ = 55;
    public static int INDEX_imu_RUA_magnetX = 56;
    public static int INDEX_imu_RUA_magnetY = 57;
    public static int INDEX_imu_RUA_magnetZ = 58;
    public static int INDEX_imu_RUA_quater1 = 59;
    public static int INDEX_imu_RUA_quater2 = 60;
    public static int INDEX_imu_RUA_quater3 = 61;
    public static int INDEX_imu_RUA_quater4 = 62;
    public static int INDEX_imu_RLA_accX = 63;
    public static int INDEX_imu_RLA_accY = 64;
    public static int INDEX_imu_RLA_accZ = 65;
    public static int INDEX_imu_RLA_gyroX = 66;
    public static int INDEX_imu_RLA_gyroY = 67;
    public static int INDEX_imu_RLA_gyroZ = 68;
    public static int INDEX_imu_RLA_magnetX = 69;
    public static int INDEX_imu_RLA_magnetY = 70;
    public static int INDEX_imu_RLA_magnetZ = 71;
    public static int INDEX_imu_RLA_quater1 = 72;
    public static int INDEX_imu_RLA_quater2 = 73;
    public static int INDEX_imu_RLA_quater3 = 74;
    public static int INDEX_imu_RLA_quater4 = 75;
    public static int INDEX_imu_LUA_accX = 76;
    public static int INDEX_imu_LUA_accY = 77;
    public static int INDEX_imu_LUA_accZ = 78;
    public static int INDEX_imu_LUA_gyroX = 79;
    public static int INDEX_imu_LUA_gyroY = 80;
    public static int INDEX_imu_LUA_gyroZ = 81;
    public static int INDEX_imu_LUA_magnetX = 82;
    public static int INDEX_imu_LUA_magnetY = 83;
    public static int INDEX_imu_LUA_magnetZ = 84;
    public static int INDEX_imu_LUA_quater1 = 85;
    public static int INDEX_imu_LUA_quater2 = 86;
    public static int INDEX_imu_LUA_quater3 = 87;
    public static int INDEX_imu_LUA_quater4 = 88;
    public static int INDEX_imu_LLA_accX = 89;
    public static int INDEX_imu_LLA_accY = 90;
    public static int INDEX_imu_LLA_accZ = 91;
    public static int INDEX_imu_LLA_gyroX = 92;
    public static int INDEX_imu_LLA_gyroY = 93;
    public static int INDEX_imu_LLA_gyroZ = 94;
    public static int INDEX_imu_LLA_magnetX = 95;
    public static int INDEX_imu_LLA_magnetY = 96;
    public static int INDEX_imu_LLA_magnetZ = 97;
    public static int INDEX_imu_LLA_quater1 = 98;
    public static int INDEX_imu_LLA_quater2 = 99;
    public static int INDEX_imu_LLA_quater3 = 100;
    public static int INDEX_imu_LLA_quater4 = 101;
    public static int INDEX_imu_LSHOE_EuX = 102;
    public static int INDEX_imu_LSHOE_EuY = 103;
    public static int INDEX_imu_LSHOE_EuZ = 104;
    public static int INDEX_imu_LSHOE_Nav_Ax = 105;
    public static int INDEX_imu_LSHOE_Nav_Ay = 106;
    public static int INDEX_imu_LSHOE_Nav_Az = 107;
    public static int INDEX_imu_LSHOE_Body_Ax = 108;
    public static int INDEX_imu_LSHOE_Body_Ay = 109;
    public static int INDEX_imu_LSHOE_Body_Az = 110;
    public static int INDEX_imu_LSHOE_AngVelBodyFrameX = 111;
    public static int INDEX_imu_LSHOE_AngVelBodyFrameY = 112;
    public static int INDEX_imu_LSHOE_AngVelBodyFrameZ = 113;
    public static int INDEX_imu_LSHOE_AngVelNavFrameX = 114;
    public static int INDEX_imu_LSHOE_AngVelNavFrameY = 115;
    public static int INDEX_imu_LSHOE_AngVelNavFrameZ = 116;
    public static int INDEX_imu_LSHOE_Compass = 117;
    public static int INDEX_imu_RSHOE_EuX = 118;
    public static int INDEX_imu_RSHOE_EuY = 119;
    public static int INDEX_imu_RSHOE_EuZ = 120;
    public static int INDEX_imu_RSHOE_Nav_Ax = 121;
    public static int INDEX_imu_RSHOE_Nav_Ay = 122;
    public static int INDEX_imu_RSHOE_Nav_Az = 123;
    public static int INDEX_imu_RSHOE_Body_Ax = 124;
    public static int INDEX_imu_RSHOE_Body_Ay = 125;
    public static int INDEX_imu_RSHOE_Body_Az = 126;
    public static int INDEX_imu_RSHOE_AngVelBodyFrameX = 127;
    public static int INDEX_imu_RSHOE_AngVelBodyFrameY = 128;
    public static int INDEX_imu_RSHOE_AngVelBodyFrameZ = 129;
    public static int INDEX_imu_RSHOE_AngVelNavFrameX = 130;
    public static int INDEX_imu_RSHOE_AngVelNavFrameY = 131;
    public static int INDEX_imu_RSHOE_AngVelNavFrameZ = 132;
    public static int INDEX_imu_RSHOE_Compass = 133;
    public static int INDEX_accelerometer_CUP_accX = 134;
    public static int INDEX_accelerometer_CUP_accX2 = 135;
    public static int INDEX_accelerometer_CUP_accX3 = 136;
    public static int INDEX_accelerometer_CUP_gyroX = 137;
    public static int INDEX_accelerometer_CUP_gyroY = 138;
    public static int INDEX_accelerometer_SALAMI_accX = 139;
    public static int INDEX_accelerometer_SALAMI_accX2 = 140;
    public static int INDEX_accelerometer_SALAMI_accX3 = 141;
    public static int INDEX_accelerometer_SALAMI_gyroX = 142;
    public static int INDEX_accelerometer_SALAMI_gyroY = 143;
    public static int INDEX_accelerometer_WATER_accX = 144;
    public static int INDEX_accelerometer_WATER_accX2 = 145;
    public static int INDEX_accelerometer_WATER_accX3 = 146;
    public static int INDEX_accelerometer_WATER_gyroX = 147;
    public static int INDEX_accelerometer_WATER_gyroY = 148;
    public static int INDEX_accelerometer_CHEESE_accX = 149;
    public static int INDEX_accelerometer_CHEESE_accX2 = 150;
    public static int INDEX_accelerometer_CHEESE_accX3 = 151;
    public static int INDEX_accelerometer_CHEESE_gyroX = 152;
    public static int INDEX_accelerometer_CHEESE_gyroY = 153;
    public static int INDEX_accelerometer_BREAD_accX = 154;
    public static int INDEX_accelerometer_BREAD_accX2 = 155;
    public static int INDEX_accelerometer_BREAD_accX3 = 156;
    public static int INDEX_accelerometer_BREAD_gyroX = 157;
    public static int INDEX_accelerometer_BREAD_gyroY = 158;
    public static int INDEX_accelerometer_KNIFE1_accX = 159;
    public static int INDEX_accelerometer_KNIFE1_accX2 = 160;
    public static int INDEX_accelerometer_KNIFE1_accX3 = 161;
    public static int INDEX_accelerometer_KNIFE1_gyroX = 162;
    public static int INDEX_accelerometer_KNIFE1_gyroY = 163;
    public static int INDEX_accelerometer_MILK_accX = 164;
    public static int INDEX_accelerometer_MILK_accX2 = 165;
    public static int INDEX_accelerometer_MILK_accX3 = 166;
    public static int INDEX_accelerometer_MILK_gyroX = 167;
    public static int INDEX_accelerometer_MILK_gyroY = 168;
    public static int INDEX_accelerometer_SPOON_accX = 169;
    public static int INDEX_accelerometer_SPOON_accX2 = 170;
    public static int INDEX_accelerometer_SPOON_accX3 = 171;
    public static int INDEX_accelerometer_SPOON_gyroX = 172;
    public static int INDEX_accelerometer_SPOON_gyroY = 173;
    public static int INDEX_accelerometer_SUGAR_accX = 174;
    public static int INDEX_accelerometer_SUGAR_accX2 = 175;
    public static int INDEX_accelerometer_SUGAR_accX3 = 176;
    public static int INDEX_accelerometer_SUGAR_gyroX = 177;
    public static int INDEX_accelerometer_SUGAR_gyroY = 178;
    public static int INDEX_accelerometer_KNIFE2_accX = 179;
    public static int INDEX_accelerometer_KNIFE2_accX2 = 180;
    public static int INDEX_accelerometer_KNIFE2_accX3 = 181;
    public static int INDEX_accelerometer_KNIFE2_gyroX = 182;
    public static int INDEX_accelerometer_KNIFE2_gyroY = 183;
    public static int INDEX_accelerometer_PLATE_accX = 184;
    public static int INDEX_accelerometer_PLATE_accX2 = 185;
    public static int INDEX_accelerometer_PLATE_accX3 = 186;
    public static int INDEX_accelerometer_PLATE_gyroX = 187;
    public static int INDEX_accelerometer_PLATE_gyroY = 188;
    public static int INDEX_accelerometer_GLASS_accX = 189;
    public static int INDEX_accelerometer_GLASS_accX2 = 190;
    public static int INDEX_accelerometer_GLASS_accX3 = 191;
    public static int INDEX_accelerometer_GLASS_gyroX = 192;
    public static int INDEX_accelerometer_GLASS_gyroY = 193;
    public static int INDEX_reed_switch_dishwasher_s1 = 194;
    public static int INDEX_reed_switch_fridge_s3 = 195;
    public static int INDEX_reed_switch_fridge_s2 = 196;
    public static int INDEX_reed_switch_fridge_s1 = 197;
    public static int INDEX_reed_switch_middledrawer_s1 = 198;
    public static int INDEX_reed_switch_middledrawer_s2 = 199;
    public static int INDEX_reed_switch_middledrawer_s3  = 200;
    public static int INDEX_reed_switch_lowerdrawer_s3 = 201;
    public static int INDEX_reed_switch_lowerdrawer_s2 = 202;
    public static int INDEX_reed_switch_upperdrawer_s3 = 203;
    public static int INDEX_reed_switch_dishwasher_s3 = 204;
    public static int INDEX_reed_switch_lowerdrawer_s1 = 205;
    public static int INDEX_reed_switch_dishwasher_s2 = 206;
    public static int INDEX_accelerometer_DOOR1_accX = 207;
    public static int INDEX_accelerometer_DOOR1_accY = 208;
    public static int INDEX_accelerometer_DOOR1_accZ = 209;
    public static int INDEX_accelerometer_LAZYCHAIR_accX = 210; 
    public static int INDEX_accelerometer_LAZYCHAIR_accY = 211;
    public static int INDEX_accelerometer_LAZYCHAIR_accZ = 212;
    public static int INDEX_accelerometer_DOOR2_accX = 213;
    public static int INDEX_accelerometer_DOOR2_accY = 214;
    public static int INDEX_accelerometer_DOOR2_accZ = 215;
    public static int INDEX_accelerometer_DISHWASHER_accX = 216; 
    public static int INDEX_accelerometer_DISHWASHER_accY = 217;
    public static int INDEX_accelerometer_DISHWASHER_accZ = 218;
    public static int INDEX_accelerometer_UPPERDRAWER_accX = 219; 
    public static int INDEX_accelerometer_UPPERDRAWER_accY = 220;
    public static int INDEX_accelerometer_UPPERDRAWER_accZ = 221;
    public static int INDEX_accelerometer_LOWERDRAWER_accX = 222; 
    public static int INDEX_accelerometer_LOWERDRAWER_accY = 223;
    public static int INDEX_accelerometer_LOWERDRAWER_accZ = 224;
    public static int INDEX_accelerometer_MIDDLEDRAWER_accX = 225; 
    public static int INDEX_accelerometer_MIDDLEDRAWER_accY = 226;
    public static int INDEX_accelerometer_MIDDLEDRAWER_accZ = 227;
    public static int INDEX_accelerometer_FRIDGE_accX = 228;
    public static int INDEX_accelerometer_FRIDGE_accY = 229;
    public static int INDEX_accelerometer_FRIDGE_accZ = 230;
    public static int INDEX_location_tag1_X = 231;
    public static int INDEX_location_tag1_Y = 232;
    public static int INDEX_location_tag1_Z = 233;
    public static int INDEX_location_tag2_X = 234;
    public static int INDEX_location_tag2_Y = 235;
    public static int INDEX_location_tag2_Z = 236;
    public static int INDEX_location_tag3_X = 237;
    public static int INDEX_location_tag3_Y = 238;
    public static int INDEX_location_tag3_Z = 239;
    public static int INDEX_location_tag4_X = 240;
    public static int INDEX_location_tag4_Y = 241;
    public static int INDEX_location_tag4_Z = 242;
    public static int INDEX_locomotion = 243;
    public static int INDEX_hlActivity = 244;
    public static int INDEX_llLeftArm = 245;
    public static int INDEX_llLeftArmObject = 246;
    public static int INDEX_llRightArm = 247;
    public static int INDEX_llRightArmObject = 248;
    public static int INDEX_mlBothArms = 249;
    
    public long timestamp = 0;
    public float accelerometer_RKN_accX = 0; 
    public float accelerometer_RKN_accY = 0;
    public float accelerometer_RKN_accZ = 0;
    public float accelerometer_HIP_accX = 0; 
    public float accelerometer_HIP_accY = 0;
    public float accelerometer_HIP_accZ = 0;
    public float accelerometer_LUA_accX = 0; 
    public float accelerometer_LUA_accY = 0;
    public float accelerometer_LUA_accZ = 0;
    public float accelerometer__RUA_accX = 0; 
    public float accelerometer__RUA_accY = 0;
    public float accelerometer__RUA_accZ = 0;
    public float accelerometer_LH_accX = 0; 
    public float accelerometer_LH_accY = 0;
    public float accelerometer_LH_accZ = 0;
    public float accelerometer_BACK_accX = 0; 
    public float accelerometer_BACK_accY = 0;
    public float accelerometer_BACK_accZ = 0;
    public float accelerometer__RKN_accX = 0; 
    public float accelerometer__RKN_accY = 0;
    public float accelerometer__RKN_accZ = 0;
    public float accelerometer_RWR_accX = 0; 
    public float accelerometer_RWR_accY = 0;
    public float accelerometer_RWR_accZ = 0;
    public float accelerometer_RUA_accX = 0; 
    public float accelerometer_RUA_accY = 0;
    public float accelerometer_RUA_accZ = 0;
    public float accelerometer__LUA_accX = 0; 
    public float accelerometer__LUA_accY = 0;
    public float accelerometer__LUA_accZ = 0;
    public float accelerometer_LWR_accX = 0; 
    public float accelerometer_LWR_accY = 0;
    public float accelerometer_LWR_accZ = 0;
    public float accelerometer_RH_accX = 0; 
    public float accelerometer_RH_accY = 0;
    public float accelerometer_RH_accZ = 0;
    public float imu_BACK_accX = 0;
    public float imu_BACK_accY = 0;
    public float imu_BACK_accZ = 0;
    public float imu_BACK_gyroX = 0;
    public float imu_BACK_gyroY = 0;
    public float imu_BACK_gyroZ = 0;
    public float imu_BACK_magnetX = 0;
    public float imu_BACK_magnetY = 0;
    public float imu_BACK_magnetZ = 0;
    public float imu_BACK_quater1 = 0;
    public float imu_BACK_quater2 = 0;
    public float imu_BACK_quater3 = 0;
    public float imu_BACK_quater4 = 0;
    public float imu_RUA_accX = 0;
    public float imu_RUA_accY = 0;
    public float imu_RUA_accZ = 0;
    public float imu_RUA_gyroX = 0;
    public float imu_RUA_gyroY = 0;
    public float imu_RUA_gyroZ = 0;
    public float imu_RUA_magnetX = 0;
    public float imu_RUA_magnetY = 0;
    public float imu_RUA_magnetZ = 0;
    public float imu_RUA_quater1 = 0;
    public float imu_RUA_quater2 = 0;
    public float imu_RUA_quater3 = 0;
    public float imu_RUA_quater4 = 0;
    public float imu_RLA_accX = 0;
    public float imu_RLA_accY = 0;
    public float imu_RLA_accZ = 0;
    public float imu_RLA_gyroX = 0;
    public float imu_RLA_gyroY = 0;
    public float imu_RLA_gyroZ = 0;
    public float imu_RLA_magnetX = 0;
    public float imu_RLA_magnetY = 0;
    public float imu_RLA_magnetZ = 0;
    public float imu_RLA_quater1 = 0;
    public float imu_RLA_quater2 = 0;
    public float imu_RLA_quater3 = 0;
    public float imu_RLA_quater4 = 0;
    public float imu_LUA_accX = 0;
    public float imu_LUA_accY = 0;
    public float imu_LUA_accZ = 0;
    public float imu_LUA_gyroX = 0;
    public float imu_LUA_gyroY = 0;
    public float imu_LUA_gyroZ = 0;
    public float imu_LUA_magnetX = 0;
    public float imu_LUA_magnetY = 0;
    public float imu_LUA_magnetZ = 0;
    public float imu_LUA_quater1 = 0;
    public float imu_LUA_quater2 = 0;
    public float imu_LUA_quater3 = 0;
    public float imu_LUA_quater4 = 0;
    public float imu_LLA_accX = 0;
    public float imu_LLA_accY = 0;
    public float imu_LLA_accZ = 0;
    public float imu_LLA_gyroX = 0;
    public float imu_LLA_gyroY = 0;
    public float imu_LLA_gyroZ = 0;
    public float imu_LLA_magnetX = 0;
    public float imu_LLA_magnetY = 0;
    public float imu_LLA_magnetZ = 0;
    public float imu_LLA_quater1 = 0;
    public float imu_LLA_quater2 = 0;
    public float imu_LLA_quater3 = 0;
    public float imu_LLA_quater4 = 0;
    public float imu_LSHOE_EuX = 0;
    public float imu_LSHOE_EuY = 0;
    public float imu_LSHOE_EuZ = 0;
    public float imu_LSHOE_Nav_Ax = 0;
    public float imu_LSHOE_Nav_Ay = 0;
    public float imu_LSHOE_Nav_Az = 0;
    public float imu_LSHOE_Body_Ax = 0;
    public float imu_LSHOE_Body_Ay = 0;
    public float imu_LSHOE_Body_Az = 0;
    public float imu_LSHOE_AngVelBodyFrameX = 0;
    public float imu_LSHOE_AngVelBodyFrameY = 0;
    public float imu_LSHOE_AngVelBodyFrameZ = 0;
    public float imu_LSHOE_AngVelNavFrameX = 0;
    public float imu_LSHOE_AngVelNavFrameY = 0;
    public float imu_LSHOE_AngVelNavFrameZ = 0;
    public float imu_LSHOE_Compass = 0;
    public float imu_RSHOE_EuX = 0;
    public float imu_RSHOE_EuY = 0;
    public float imu_RSHOE_EuZ = 0;
    public float imu_RSHOE_Nav_Ax = 0;
    public float imu_RSHOE_Nav_Ay = 0;
    public float imu_RSHOE_Nav_Az = 0;
    public float imu_RSHOE_Body_Ax = 0;
    public float imu_RSHOE_Body_Ay = 0;
    public float imu_RSHOE_Body_Az = 0;
    public float imu_RSHOE_AngVelBodyFrameX = 0;
    public float imu_RSHOE_AngVelBodyFrameY = 0;
    public float imu_RSHOE_AngVelBodyFrameZ = 0;
    public float imu_RSHOE_AngVelNavFrameX = 0;
    public float imu_RSHOE_AngVelNavFrameY = 0;
    public float imu_RSHOE_AngVelNavFrameZ = 0;
    public float imu_RSHOE_Compass = 0;
    public float accelerometer_CUP_accX = 0;
    public float accelerometer_CUP_accX2 = 0;
    public float accelerometer_CUP_accX3 = 0;
    public float accelerometer_CUP_gyroX = 0;
    public float accelerometer_CUP_gyroY = 0;
    public float accelerometer_SALAMI_accX = 0;
    public float accelerometer_SALAMI_accX2 = 0;
    public float accelerometer_SALAMI_accX3 = 0;
    public float accelerometer_SALAMI_gyroX = 0;
    public float accelerometer_SALAMI_gyroY = 0;
    public float accelerometer_WATER_accX = 0;
    public float accelerometer_WATER_accX2 = 0;
    public float accelerometer_WATER_accX3 = 0;
    public float accelerometer_WATER_gyroX = 0;
    public float accelerometer_WATER_gyroY = 0;
    public float accelerometer_CHEESE_accX = 0;
    public float accelerometer_CHEESE_accX2 = 0;
    public float accelerometer_CHEESE_accX3 = 0;
    public float accelerometer_CHEESE_gyroX = 0;
    public float accelerometer_CHEESE_gyroY = 0;
    public float accelerometer_BREAD_accX = 0;
    public float accelerometer_BREAD_accX2 = 0;
    public float accelerometer_BREAD_accX3 = 0;
    public float accelerometer_BREAD_gyroX = 0;
    public float accelerometer_BREAD_gyroY = 0;
    public float accelerometer_KNIFE1_accX = 0;
    public float accelerometer_KNIFE1_accX2 = 0;
    public float accelerometer_KNIFE1_accX3 = 0;
    public float accelerometer_KNIFE1_gyroX = 0;
    public float accelerometer_KNIFE1_gyroY = 0;
    public float accelerometer_MILK_accX = 0;
    public float accelerometer_MILK_accX2 = 0;
    public float accelerometer_MILK_accX3 = 0;
    public float accelerometer_MILK_gyroX = 0;
    public float accelerometer_MILK_gyroY = 0;
    public float accelerometer_SPOON_accX = 0;
    public float accelerometer_SPOON_accX2 = 0;
    public float accelerometer_SPOON_accX3 = 0;
    public float accelerometer_SPOON_gyroX = 0;
    public float accelerometer_SPOON_gyroY = 0;
    public float accelerometer_SUGAR_accX = 0;
    public float accelerometer_SUGAR_accX2 = 0;
    public float accelerometer_SUGAR_accX3 = 0;
    public float accelerometer_SUGAR_gyroX = 0;
    public float accelerometer_SUGAR_gyroY = 0;
    public float accelerometer_KNIFE2_accX = 0;
    public float accelerometer_KNIFE2_accX2 = 0;
    public float accelerometer_KNIFE2_accX3 = 0;
    public float accelerometer_KNIFE2_gyroX = 0;
    public float accelerometer_KNIFE2_gyroY = 0;
    public float accelerometer_PLATE_accX = 0;
    public float accelerometer_PLATE_accX2 = 0;
    public float accelerometer_PLATE_accX3 = 0;
    public float accelerometer_PLATE_gyroX = 0;
    public float accelerometer_PLATE_gyroY = 0;
    public float accelerometer_GLASS_accX = 0;
    public float accelerometer_GLASS_accX2 = 0;
    public float accelerometer_GLASS_accX3 = 0;
    public float accelerometer_GLASS_gyroX = 0;
    public float accelerometer_GLASS_gyroY = 0;
    public boolean reed_switch_dishwasher_s1 = false;
    public boolean reed_switch_fridge_s3 = false;
    public boolean reed_switch_fridge_s2 = false;
    public boolean reed_switch_fridge_s1 = false;
    public boolean reed_switch_middledrawer_s1 = false;
    public boolean reed_switch_middledrawer_s2 = false;
    public boolean reed_switch_middledrawer_s3  = false;
    public boolean reed_switch_lowerdrawer_s3 = false;
    public boolean reed_switch_lowerdrawer_s2 = false;
    public boolean reed_switch_upperdrawer_s3 = false;
    public boolean reed_switch_dishwasher_s3 = false;
    public boolean reed_switch_lowerdrawer_s1 = false;
    public boolean reed_switch_dishwasher_s2 = false;
    public float accelerometer_DOOR1_accX = 0;
    public float accelerometer_DOOR1_accY = 0;
    public float accelerometer_DOOR1_accZ = 0;
    public float accelerometer_LAZYCHAIR_accX = 0; 
    public float accelerometer_LAZYCHAIR_accY = 0;
    public float accelerometer_LAZYCHAIR_accZ = 0;
    public float accelerometer_DOOR2_accX = 0;
    public float accelerometer_DOOR2_accY = 0;
    public float accelerometer_DOOR2_accZ = 0;
    public float accelerometer_DISHWASHER_accX = 0; 
    public float accelerometer_DISHWASHER_accY = 0;
    public float accelerometer_DISHWASHER_accZ = 0;
    public float accelerometer_UPPERDRAWER_accX = 0; 
    public float accelerometer_UPPERDRAWER_accY = 0;
    public float accelerometer_UPPERDRAWER_accZ = 0;
    public float accelerometer_LOWERDRAWER_accX = 0; 
    public float accelerometer_LOWERDRAWER_accY = 0;
    public float accelerometer_LOWERDRAWER_accZ = 0;
    public float accelerometer_MIDDLEDRAWER_accX = 0; 
    public float accelerometer_MIDDLEDRAWER_accY = 0;
    public float accelerometer_MIDDLEDRAWER_accZ = 0;
    public float accelerometer_FRIDGE_accX = 0;
    public float accelerometer_FRIDGE_accY = 0;
    public float accelerometer_FRIDGE_accZ = 0;
    public float location_tag1_X = 0;
    public float location_tag1_Y = 0;
    public float location_tag1_Z = 0;
    public float location_tag2_X = 0;
    public float location_tag2_Y = 0;
    public float location_tag2_Z = 0;
    public float location_tag3_X = 0;
    public float location_tag3_Y = 0;
    public float location_tag3_Z = 0;
    public float location_tag4_X = 0;
    public float location_tag4_Y = 0;
    public float location_tag4_Z = 0;
    public Locomotion locomotion = Locomotion.Null;
    public HL_Activity hlActivity = HL_Activity.Null;
    public LL_Left_Arm llLeftArm = LL_Left_Arm.Null;
    public LL_Left_Arm_Object llLeftArmObject = LL_Left_Arm_Object.Null;
    public LL_Right_Arm llRightArm = LL_Right_Arm.Null;
    public LL_Right_Arm_Object llRightArmObject = LL_Right_Arm_Object.Null;
    public ML_Both_Arms mlBothArms = ML_Both_Arms.Null;
    
    
    public static enum Locomotion {
        Stand("1"),
        Walk("2"),
        Sit("4"),
        Lie("5"),
        Null("0");
        
        public final String label;
        
        private Locomotion (String loc) {
            label = loc;
        }
        public static Locomotion value (String l) {
            if (l.contentEquals(Locomotion.Lie.label)) return Lie;
            else if (l.contentEquals(Locomotion.Sit.label)) return Sit;
            else if (l.contentEquals(Locomotion.Stand.label)) return Stand;
            else if (l.contentEquals(Locomotion.Walk.label)) return Walk;
            return Null;
        }
    }
    public static enum HL_Activity {
        Relaxing("101"),
        Coffee_time("102"),
        Early_morning("103"),
        Cleanup("104"),
        Sandwich_time("105"),
        Null("0");
        
        public final String label;
        
        private HL_Activity (String act) {
            label = act;
        }
        public static HL_Activity value (String l) {
            if (l.contentEquals(HL_Activity.Cleanup.label)) return Cleanup;
            else if (l.contentEquals(HL_Activity.Coffee_time.label)) return Coffee_time;
            else if (l.contentEquals(HL_Activity.Early_morning.label)) return Early_morning;
            else if (l.contentEquals(HL_Activity.Relaxing.label)) return Relaxing;
            else if (l.contentEquals(HL_Activity.Sandwich_time.label)) return Sandwich_time;
            return Null;
        }
    }
    public static enum ML_Both_Arms {
        Open_Door_1("406516"),
        Open_Door_2("406517"),
        Close_Door_1("404516"),
        Close_Door_2("404517"),
        Open_Fridge("406520"),
        Close_Fridge("404520"),
        Open_Dishwasher("406505"),
        Close_Dishwasher("404505"),
        Open_Drawer_1("406519"),
        Close_Drawer_1("404519"),
        Open_Drawer_2("406511"),
        Close_Drawer_2("404511"),
        Open_Drawer_3("406508"),
        Close_Drawer_3("404508"),
        Clean_Table("408512"),
        Drink_from_Cup("407521"),
        Toggle_Switch("405506"),
        Null("0");
        
        public final String label;
        
        private ML_Both_Arms (String act) {
            label = act;
        }
        public static ML_Both_Arms value (String l) {
            if (l.contentEquals(ML_Both_Arms.Clean_Table.label)) return Clean_Table;
            else if (l.contentEquals(ML_Both_Arms.Close_Dishwasher.label)) return Close_Dishwasher;
            else if (l.contentEquals(ML_Both_Arms.Close_Door_1.label)) return Close_Door_1;
            else if (l.contentEquals(ML_Both_Arms.Close_Door_2.label)) return Close_Door_2;
            else if (l.contentEquals(ML_Both_Arms.Close_Drawer_1.label)) return Close_Drawer_1;
            else if (l.contentEquals(ML_Both_Arms.Close_Drawer_2.label)) return Close_Drawer_2;
            else if (l.contentEquals(ML_Both_Arms.Close_Drawer_3.label)) return Close_Drawer_3;
            else if (l.contentEquals(ML_Both_Arms.Close_Fridge.label)) return Close_Fridge;
            else if (l.contentEquals(ML_Both_Arms.Drink_from_Cup.label)) return Drink_from_Cup;
            else if (l.contentEquals(ML_Both_Arms.Open_Dishwasher.label)) return Open_Dishwasher;
            else if (l.contentEquals(ML_Both_Arms.Open_Door_1.label)) return Open_Door_1;
            else if (l.contentEquals(ML_Both_Arms.Open_Door_2.label)) return Open_Door_2;
            else if (l.contentEquals(ML_Both_Arms.Open_Drawer_1.label)) return Open_Drawer_1;
            else if (l.contentEquals(ML_Both_Arms.Open_Drawer_2.label)) return Open_Drawer_2;
            else if (l.contentEquals(ML_Both_Arms.Open_Drawer_3.label)) return Open_Drawer_3;
            else if (l.contentEquals(ML_Both_Arms.Open_Fridge.label)) return Open_Fridge;
            else if (l.contentEquals(ML_Both_Arms.Toggle_Switch.label)) return Toggle_Switch;
            return Null;
        }
    }
    public static enum LL_Left_Arm_Object {
        Bottle("301"),
        Salami("302"),
        Bread("303"),
        Sugar("304"),
        Dishwasher("305"),
        Switch("306"),
        Milk("307"),
        Drawer3_lower("308"),
        Spoon("309"),
        Knife_cheese("310"),
        Drawer2_middle("311"),
        Table("312"),
        Glass("313"),
        Cheese("314"),
        Chair("315"),
        Door1("316"),
        Door2("317"),
        Plate("318"),
        Drawer1_top("319"),
        Fridge("320"),
        Cup("321"),
        Knife_salami("322"),
        Lazychair("323"),
        Null("0");
        
        public final String label;
        
        private LL_Left_Arm_Object (String la) {
            label = la;
        }
        public static LL_Left_Arm_Object value (String l) {
            if (l.contentEquals(LL_Left_Arm_Object.Bottle.label)) return Bottle;
            else if (l.contentEquals(LL_Left_Arm_Object.Bread.label)) return Bread;
            else if (l.contentEquals(LL_Left_Arm_Object.Chair.label)) return Chair;
            else if (l.contentEquals(LL_Left_Arm_Object.Cheese.label)) return Cheese;
            else if (l.contentEquals(LL_Left_Arm_Object.Cup.label)) return Cup;
            else if (l.contentEquals(LL_Left_Arm_Object.Dishwasher.label)) return Dishwasher;
            else if (l.contentEquals(LL_Left_Arm_Object.Door1.label)) return Door1;
            else if (l.contentEquals(LL_Left_Arm_Object.Door2.label)) return Door2;
            else if (l.contentEquals(LL_Left_Arm_Object.Drawer1_top.label)) return Drawer1_top;
            else if (l.contentEquals(LL_Left_Arm_Object.Drawer2_middle.label)) return Drawer2_middle;
            else if (l.contentEquals(LL_Left_Arm_Object.Drawer3_lower.label)) return Drawer3_lower;
            else if (l.contentEquals(LL_Left_Arm_Object.Fridge.label)) return Fridge;
            else if (l.contentEquals(LL_Left_Arm_Object.Glass.label)) return Glass;
            else if (l.contentEquals(LL_Left_Arm_Object.Knife_cheese.label)) return Knife_cheese;
            else if (l.contentEquals(LL_Left_Arm_Object.Knife_salami.label)) return Knife_salami;
            else if (l.contentEquals(LL_Left_Arm_Object.Lazychair.label)) return Lazychair;
            else if (l.contentEquals(LL_Left_Arm_Object.Milk.label)) return Milk;
            else if (l.contentEquals(LL_Left_Arm_Object.Plate.label)) return Plate;
            else if (l.contentEquals(LL_Left_Arm_Object.Salami.label)) return Salami;
            else if (l.contentEquals(LL_Left_Arm_Object.Spoon.label)) return Spoon;
            else if (l.contentEquals(LL_Left_Arm_Object.Sugar.label)) return Sugar;
            else if (l.contentEquals(LL_Left_Arm_Object.Switch.label)) return Switch;
            else if (l.contentEquals(LL_Left_Arm_Object.Table.label)) return Table;
            return Null;
        }
    }
    public static enum LL_Right_Arm_Object {
        Bottle("501"),
        Salami("502"),
        Bread("503"),
        Sugar("504"),
        Dishwasher("505"),
        Switch("506"),
        Milk("507"),
        Drawer3_lower("508"),
        Spoon("509"),
        Knife_cheese("510"),
        Drawer2_middle("511"),
        Table("512"),
        Glass("513"),
        Cheese("514"),
        Chair("515"),
        Door1("516"),
        Door2("517"),
        Plate("518"),
        Drawer1_top("519"),
        Fridge("520"),
        Cup("521"),
        Knife_salami("522"),
        Lazychair("523"),
        Null("0");
        
        public final String label;
        
        private LL_Right_Arm_Object (String la) {
            label = la;
        }
        public static LL_Right_Arm_Object value (String l) {
            if (l.contentEquals(LL_Right_Arm_Object.Bottle.label)) return Bottle;
            else if (l.contentEquals(LL_Right_Arm_Object.Bread.label)) return Bread;
            else if (l.contentEquals(LL_Right_Arm_Object.Chair.label)) return Chair;
            else if (l.contentEquals(LL_Right_Arm_Object.Cheese.label)) return Cheese;
            else if (l.contentEquals(LL_Right_Arm_Object.Cup.label)) return Cup;
            else if (l.contentEquals(LL_Right_Arm_Object.Dishwasher.label)) return Dishwasher;
            else if (l.contentEquals(LL_Right_Arm_Object.Door1.label)) return Door1;
            else if (l.contentEquals(LL_Right_Arm_Object.Door2.label)) return Door2;
            else if (l.contentEquals(LL_Right_Arm_Object.Drawer1_top.label)) return Drawer1_top;
            else if (l.contentEquals(LL_Right_Arm_Object.Drawer2_middle.label)) return Drawer2_middle;
            else if (l.contentEquals(LL_Right_Arm_Object.Drawer3_lower.label)) return Drawer3_lower;
            else if (l.contentEquals(LL_Right_Arm_Object.Fridge.label)) return Fridge;
            else if (l.contentEquals(LL_Right_Arm_Object.Glass.label)) return Glass;
            else if (l.contentEquals(LL_Right_Arm_Object.Knife_cheese.label)) return Knife_cheese;
            else if (l.contentEquals(LL_Right_Arm_Object.Knife_salami.label)) return Knife_salami;
            else if (l.contentEquals(LL_Right_Arm_Object.Lazychair.label)) return Lazychair;
            else if (l.contentEquals(LL_Right_Arm_Object.Milk.label)) return Milk;
            else if (l.contentEquals(LL_Right_Arm_Object.Plate.label)) return Plate;
            else if (l.contentEquals(LL_Right_Arm_Object.Salami.label)) return Salami;
            else if (l.contentEquals(LL_Right_Arm_Object.Spoon.label)) return Spoon;
            else if (l.contentEquals(LL_Right_Arm_Object.Sugar.label)) return Sugar;
            else if (l.contentEquals(LL_Right_Arm_Object.Switch.label)) return Switch;
            else if (l.contentEquals(LL_Right_Arm_Object.Table.label)) return Table;
            return Null;
        }
    }
    public static enum LL_Left_Arm {
        unlock("201"),
        stir("202"),
        lock("203"),
        close("204"),
        reach("205"),
        open("206"),
        sip("207"),
        clean("208"),
        bite("209"),
        cut("210"),
        spread("211"),
        release("212"),
        move("213"),
        Null("0");
        
        public final String label;
        
        private LL_Left_Arm (String la) {
            label = la;
        }
        public static LL_Left_Arm value (String l) {
            if (l.contentEquals(LL_Left_Arm.bite.label)) return bite;
            else if (l.contentEquals(LL_Left_Arm.clean.label)) return clean;
            else if (l.contentEquals(LL_Left_Arm.close.label)) return close;
            else if (l.contentEquals(LL_Left_Arm.cut.label)) return cut;
            else if (l.contentEquals(LL_Left_Arm.lock.label)) return lock;
            else if (l.contentEquals(LL_Left_Arm.move.label)) return move;
            else if (l.contentEquals(LL_Left_Arm.open.label)) return open;
            else if (l.contentEquals(LL_Left_Arm.reach.label)) return reach;
            else if (l.contentEquals(LL_Left_Arm.release.label)) return release;
            else if (l.contentEquals(LL_Left_Arm.sip.label)) return sip;
            else if (l.contentEquals(LL_Left_Arm.spread.label)) return spread;
            else if (l.contentEquals(LL_Left_Arm.stir.label)) return stir;
            else if (l.contentEquals(LL_Left_Arm.unlock.label)) return unlock;
            return Null;
        }
    }
    public static enum LL_Right_Arm {
        unlock("401"),
        stir("402"),
        lock("403"),
        close("404"),
        reach("405"),
        open("406"),
        sip("407"),
        clean("408"),
        bite("409"),
        cut("410"),
        spread("411"),
        release("412"),
        move("413"),
        Null("0");
        
        public final String label;
        
        private LL_Right_Arm (String ra) {
            label = ra;
        }
        public static LL_Right_Arm value (String l) {
            if (l.contentEquals(LL_Right_Arm.bite.label)) return bite;
            else if (l.contentEquals(LL_Right_Arm.clean.label)) return clean;
            else if (l.contentEquals(LL_Right_Arm.close.label)) return close;
            else if (l.contentEquals(LL_Right_Arm.cut.label)) return cut;
            else if (l.contentEquals(LL_Right_Arm.lock.label)) return lock;
            else if (l.contentEquals(LL_Right_Arm.move.label)) return move;
            else if (l.contentEquals(LL_Right_Arm.open.label)) return open;
            else if (l.contentEquals(LL_Right_Arm.reach.label)) return reach;
            else if (l.contentEquals(LL_Right_Arm.release.label)) return release;
            else if (l.contentEquals(LL_Right_Arm.sip.label)) return sip;
            else if (l.contentEquals(LL_Right_Arm.spread.label)) return spread;
            else if (l.contentEquals(LL_Right_Arm.stir.label)) return stir;
            else if (l.contentEquals(LL_Right_Arm.unlock.label)) return unlock;
            return Null;
        }
    }
}
