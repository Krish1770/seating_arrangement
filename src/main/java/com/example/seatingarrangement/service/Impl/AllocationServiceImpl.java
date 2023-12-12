package com.example.seatingarrangement.service.Impl;


import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.entity.LayOut;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.LayOutRepository;
import com.example.seatingarrangement.repository.Service.AllocationRepoService;
import com.example.seatingarrangement.repository.Service.LayOutRepoService;
import com.example.seatingarrangement.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private AllocationRepoService allocationRepoService;

    @Autowired
    private LayOutRepository layOutRepository;
    @Autowired
    private LayOutRepoService layOutRepoService;

    public static int[][] arr;

    public static int[][] dp;

    public static int availableSpaces=0;

    public static String[][] teamNames;

    public static LinkedHashMap <String,Character>  tempTeamList=new LinkedHashMap<>();

    public static ArrayList<String> midValues=new ArrayList<String>();


    @Override
    public ResponseEntity<ResponseDto> add(LayoutDto layoutDto) {

        log.info(layoutDto.toString());
        LayOut layout = new LayOut();
        layout.setCompanyName(layoutDto.getCompanyName());
        layout.setLayOut(layoutDto.getLayOut());
        layout.setTeamIdList(new LinkedHashMap<String, Character>());
        availableSpaces=availableSpacesCount(layout.getLayOut());
        layout.setAvailableSpaces(availableSpaces);
        layOutRepoService.insert(layout);
        log.info(layout.toString());


        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("", "layout saved", HttpStatus.OK));


    }

    private int availableSpacesCount(int[][] layOut) {

        int total=0;
        for(int i=0;i<layOut.length;i++)
        {
            String m= Arrays.toString(layOut[i]);

            String h=m.replace("1","");

            total+=m.length()-h.length();
        }

        System.out.println(total);
        return total;
    }

    @Override
    public   ResponseEntity<ResponseDto> addAllocation(AllocationDto allocationDto) {
        String companyName = allocationDto.getCompanyName();


        Optional<LayOut> layOut = layOutRepoService.findByCompanyName(companyName);
        LinkedHashMap<String, Character> teamList = new LinkedHashMap<>();
     int spacesTobeAllocated=0;
        LinkedHashMap<String, Integer> map = allocationDto.getToBeAllocated();

        System.out.println(layOut.toString());
        int row = layOut.get().getLayOut().length;

        int col = layOut.get().getLayOut()[0].length;


        for(int i:map.values())
        {
            spacesTobeAllocated+=i;
        }

        availableSpaces=layOut.get().getAvailableSpaces();
        System.out.println(spacesTobeAllocated+" "+availableSpaces);
        if(availableSpaces<spacesTobeAllocated)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("","memebers exceeds the spaces",HttpStatus.OK));

        for (String name : map.keySet()) {

            if (layOut.get().getTeamIdList().isEmpty()) {
                layOut.get().getTeamIdList().put(name, 'A');


            } else {
                teamList = layOut.get().getTeamIdList();
                char lastId = teamList.values().stream().toList().get(teamList.size() - 1);
                ++lastId;
                layOut.get().getTeamIdList().put(name, lastId);

            }


        }






         tempTeamList=layOut.get().getTeamIdList();
        SeatingCalculationDto seatingCalculationDto = new SeatingCalculationDto(layOut.get().getLayOut(), teamList, map);
        seatingCalculation(seatingCalculationDto);
        log.info(layOut.get().getTeamIdList().toString());


        Allocation allocation=new Allocation(layOut.get().getId(),layOut.get().getCompanyName(),teamNames);

        UserReferenceDto userReferenceDto=new UserReferenceDto(teamNames,tempTeamList);


        allocationRepository.save(allocation);
//        layOutRepository.save(layOut.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(userReferenceDto,"allocation created",HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ResponseDto> getDivumLayout() {

        String companyName="Divum";
        LayOut layOut=layOutRepository.findByCompanyName(companyName).get();
        System.out.println("abc");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(layOut,"layout retrieved",HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ResponseDto> addTeamList(String teamName,List<TeamDto> teamDtoList) {

        LayOut layOut=layOutRepository.findByCompanyName(teamName).get();

        layOut.setTeamDtoList(teamDtoList);

        layOutRepository.save(layOut);
        return null;
    }

    private void seatingCalculation(SeatingCalculationDto seatingCalculationDto) {

        arr = seatingCalculationDto.getLayOut();

        teamNames = new String[arr.length][arr[0].length];
        LinkedHashMap<String, Integer> map = seatingCalculationDto.getToBeAllocated();

        boolean[][] booleans = new boolean[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr[0].length; j++) {
                booleans[i][j] = arr[i][j] == 1;
            }
        }

        ArrayList<Integer> al = new ArrayList<Integer>(map.values().stream().toList());


        Collections.sort(al);

        for (int i = al.size() - 1; i >= 0; i--) {
            int lesserThanValue = 0;
            int gOfLesserThanValue = 0;
            midValues.clear();
            String key = getValueByKey(map, al.get(i));
            int hOfLesserThanValue = 0;
            dp = Dpcalculation(arr);
            int completedFlag;
            boolean[][] temp = booleans;
            int sameValue = 0;

            ArrayList<String> arrayList = new ArrayList<String>();

            while (al.get(i) > 0) {

                int m=0;

                int firstIncomingFlag = 0;
                int equalityFlag = 0;
                int firstNearFlag = 0;

                dp = Dpcalculation(arr);
//                System.out.println("non-stop");
                completedFlag = 0;


//                for (int j = 0; j < arr.length; j++) {
//                    for (int k = 0; k < arr[j].length; k++) {
                int value = al.get(i);
                int h = 1;
                int g = 1;
                int tempg = 0;
                int temph = 0;
//                System.out.println(key + "" + value);
//                if(sameValue==1)
//                {
                lesserThanValue = 0;
//                    if(hOfLesserThanValue-1>=0)
//                        h=hOfLesserThanValue-1;
//                    else
//                        h=hOfLesserThanValue;

//                    if(gOfLesserThanValue-1>=0)
//                        g=gOfLesserThanValue-1;
//                    else
//                        g=gOfLesserThanValue;
//                }
                while (h < dp.length) {

//                            String m= Arrays.toString(dp[h]);
//                    System.out.println("stop");

                    g = 1;
//
//                         if(sameValue==1) {
//                             if (gOfLesserThanValue - 1 >= 0)
//                                 g = gOfLesserThanValue - 1;
//                             else
//                                 g = gOfLesserThanValue;
//                         }
//                                if(m.contains(value+""))
//                                {
                    while (g < dp[h].length) {

//                        System.out.println("inside g loop");

                        if (dp[h][g] != 0 && dp[h][g]<=value) {
                            if (dp[h][g] == value) {
                                int borderColumn = g;
                                equalityFlag = 1;
                                if (arrayList.isEmpty()) {
                                    changingTrueToFalse(booleans, dp, g - 1, h - 1, key, value);
                                    arrayList.add(+g + "_" + h);
                                    map.replace(key, 0);
                                    completedFlag = 1;
                                    h = dp.length - 1;
                                    al.set(i, 0);
                                    m=1;
                                    value=0;
                                    break;

                                } else {
                                    if(firstIncomingFlag==0)
                                    {
                                        tempg=g;
                                        temph=h;
                                        firstIncomingFlag=1;
                                    }
                                    if(firstIncomingFlag==1)
                                    {
                                        String nearValue = nearCluster( temph + "_" + tempg, h + "_" +g);


//
                                        String[] tempArray = nearValue.split("_");

                                        temph = Integer.parseInt(tempArray[0]);
                                        tempg = Integer.parseInt(tempArray[1]);
                                    }
                                }


                            } else {
                                if (firstIncomingFlag == 0) {
                                    firstIncomingFlag = 1;
                                    temph = h;
                                    tempg = g;
                                } else if(!arrayList.isEmpty()){
                                    String nearValue = nearCluster( temph + "_" + tempg, h + "_" + g);
//
                                    String[] tempArray = nearValue.split("_");

                                    temph = Integer.parseInt(tempArray[0]);
                                    tempg = Integer.parseInt(tempArray[1]);                                }

                                else
                                {
                                    if(dp[h][g]>dp[temph][tempg])
                                    {
                                        temph=h;
                                        tempg=g;
                                    }
                                }
                            }


                        }



//                            else if (firstNearFlag == 1) {
//                                completedFlag = 3;
//                                String nearValue = distanceCalculator(arrayList, tempg + "_" + temph, g + "_" + h);
//
//                                String[] tempArray = nearValue.split("_");
//
//                                tempg = Integer.parseInt(tempArray[0]);
//                                temph = Integer.parseInt(tempArray[1]);
//
//
//                            } else if (firstNearFlag == 0) {
//                                completedFlag = 2;
//                                firstNearFlag = 1;
//                                tempg = g;
//                                temph = h;
//                            }


//                                    k = arr[0].length - 1;
//                                    j = arr.length - 1;
//                                    break;


//                        else
//                        {
////                                    if(equalityFlag!=1)
////                                    {
//                            if (dp[h][g] < value && dp[h][g] > lesserThanValue) {
//                                completedFlag=4;
//                                lesserThanValue = dp[h][g];
//                                gOfLesserThanValue = g;
//                                hOfLesserThanValue = h;
//                            } else if (lesserThanValue != 0 && lesserThanValue == dp[h][g] && !arrayList.isEmpty()) {
//                                String NearValue = distanceCalculator(arrayList, gOfLesserThanValue + "_" + hOfLesserThanValue, g + "_" + h);
//                                String[] tempArray = NearValue.split("_");
//
//                                gOfLesserThanValue = Integer.parseInt(tempArray[0]);
//                                hOfLesserThanValue = Integer.parseInt(tempArray[1]);
//                            }
////                                }
//                        }

//                                    }
                        g++;
                    }
                    h++;
                }

                if(firstIncomingFlag==1 && m==0)
                {
                    changingTrueToFalse(booleans, dp, tempg - 1, temph - 1, key, value);
                    arrayList.add(tempg + "_" + temph);
                    map.replace(key, value - dp[temph][tempg]);
                    al.set(i, al.get(i) - dp[temph][tempg]);
                }
//                    }
//                }

//                    if (!arrayList.isEmpty() && lesserThanValue != 0) {
//                        String NearValue = distanceCalculator(arrayList, gOfLesserThanValue + "_" + hOfLesserThanValue, tempg + "_" + temph);
//                        String[] tempArray = NearValue.split("_");
//
//                        gOfLesserThanValue = Integer.parseInt(tempArray[0]);
//                        hOfLesserThanValue = Integer.parseInt(tempArray[1]);
//                        changingTrueToFalse(booleans, dp, gOfLesserThanValue - 1, hOfLesserThanValue - 1, key, value);
//                        arrayList.add(gOfLesserThanValue + "_" + hOfLesserThanValue);
//                        map.replace(key, value - dp[hOfLesserThanValue][gOfLesserThanValue]);
//                        al.set(i, al.get(i) - dp[hOfLesserThanValue][gOfLesserThanValue]);
//                    }
//                    if (completedFlag == 0) {
//                        sameValue = 1;
//                        h = hOfLesserThanValue;
//                        g = gOfLesserThanValue;
//                        changingTrueToFalse(booleans, dp, g - 1, h - 1, key, value);
//                        arrayList.add(+g + "_" + h);
//                        map.replace(key, value - dp[g][h]);
//                        al.set(i, al.get(i) - dp[g][h]);
//                    }

//                    if (completedFlag == 2) {
//                        changingTrueToFalse(booleans, dp, gOfLesserThanValue - 1, hOfLesserThanValue - 1, key, value);
//                        arrayList.add(+gOfLesserThanValue + "_" + hOfLesserThanValue);
//                        al.set(i, al.get(i) - dp[hOfLesserThanValue][gOfLesserThanValue]);
//                    }
//                    if (completedFlag == 3) {
//                        changingTrueToFalse(booleans, dp, tempg - 1, temph - 1, key, value);
//                        arrayList.add(tempg + "_" + temph);
//                        map.replace(key, value - dp[temph][temph]);
//                        al.set(i, al.get(i) - dp[temph][tempg]);
//
//                    }
//                    if (completedFlag == 4) {
//                        changingTrueToFalse(booleans, dp, gOfLesserThanValue - 1, hOfLesserThanValue - 1, key, value);
//                        arrayList.add(tempg + "_" + temph);
//                        arrayList.add(gOfLesserThanValue + "_" + hOfLesserThanValue);
//                        al.set(i, al.get(i) - dp[hOfLesserThanValue][gOfLesserThanValue]);
//                    changingTrueToFalse(booleans,dp,tempg-1,temph-1,key);
//                    arrayList.add(tempg+"_"+temph);
//                    map.replace(key,value-dp[temph][temph]);
//                    al.set(i,al.get(i)-dp[temph][tempg]);
//                    }
            }
        }



    }

    private String distanceCalculator(ArrayList<String> arr, String point1, String point2) {
        String lastPoint= arr.get(arr.size()-1);

        String[] arrayA=lastPoint.split("_");

        String[] arrayX= point1.split("_");

        String[] arrayY=point2.split("_");

        int diffOfAX=Math.abs(Integer.parseInt(arrayA[0])-Integer.parseInt(arrayX[0]))
                +Math.abs(Integer.parseInt(arrayA[1])-Integer.parseInt((arrayX[1])));

        int diffOfAY=Math.abs(Integer.parseInt(arrayA[0])-Integer.parseInt(arrayY[0]))
                +Math.abs(Integer.parseInt(arrayA[1])-Integer.parseInt((arrayY[1])));

        if(diffOfAX>diffOfAY)
            return point2;

        else
            return point1;
    }

    private static boolean[][] changingTrueToFalse ( boolean[][] booleans, int[][] dp, int column, int row, String
            key,int value){
        int starting_point = 0;
        int ending_point = 0;
        System.out.println(key+" "+value);
        int copyOfValue2=value;
        String x4 = (row + 1) + "_" + (1 + column);
        String x2 = x4;
        String x1=x4;
        String x3=x4;
        int h=0;

        int x3Flg=0;
        int x2Flg=0;
        int copyOfValue = value;
        int comeOut = 0;
        int copyOfColumn = column;
        int prevValueOfj=0;
        int preOfjChecker=0;
       int countOf1=0;
        int firstValueInRow=0;

        System.out.println("changing" + column + "" + row);

        for (int i = row; i >= 0; i--) {
            x2Flg=0;

            if(i!=row && countOf1==0) {
                break;
            }
                countOf1=0;
                if(i!=row)
                {
                    preOfjChecker=prevValueOfj;
                }

            copyOfColumn--;
            for (int j = column; j >= 0; j--) {


//                   if(firstValueInRow==0 && dp[i+1][j+1]>0)
//                   {
//                       firstValueInRow=j;
//                       column=firstValueInRow;
//                   }
                   if(firstValueInRow==0 && dp[i+1][j+1]==0)
                   {
                       x3Flg=1;
                       firstValueInRow=j+1;
                       break;
                   }
                    if(j==preOfjChecker  && dp[i][j+1]==0 && dp[i+1][j]==0)
                    {
                        if(dp[i+1][j+1]==0)
                         break;
                    }

                    if(j<preOfjChecker && dp[i][j+1]==0 && dp[i+1][j+1]==0)
                    {
                        preOfjChecker=j;
                        break;
                    }
                    if (dp[i + 1][j + 1] == 0) {
                     x3Flg=1;
                     continue;

                }

                     countOf1++;
//                if(dp[i+1][j+1]==0) {
//                    x3Flg=1;
//                    break;
//                }

                 copyOfValue--;
                if (copyOfValue == 0) {
                    comeOut = 1;
                }

                booleans[i][j] = false;

                arr[i][j] = 0;
                x1=(i+1)+"_"+(j+1);

                if(x3Flg==0) {
                    x3 = (i + 1) + "_" + (j + 1);
//                        x3Flg=1;
                }

                if(x2Flg==0) {
                    x2 = (i + 1) + "_" + (j + 1);
                    x2Flg=1;
                }


                         prevValueOfj=j;
                teamNames[i][j] = tempTeamList.get(key)+""+copyOfValue2;
                copyOfValue2--;
                System.out.println(dp[i+1][j+1]);

            }
            if (comeOut == 1)
                break;


        }

//        String x1=(column-columnChange+1)+"_"+(row-rowChange+1);
//        String x2=(column-columnChange+1)+"_"+(row+1);
//        String x3=(column+1)+"_"+(row-rowChange+1);
//        String x4=(column+1)+"_"+(row+1);
        printTeamNames(teamNames);
        calculateMidPt(x1,x2,x3,x4);

        System.out.println(x1+" "+x2+" "+x3+" "+x4);
        return null;

    }

    public static void calculateMidPt(String x1,String x2,String x3,String x4)
    {
        String[]  ArrayX1=x1.split("_");
        String[]  ArrayX2=x2.split("_");
        String[]  ArrayX3=x3.split("_");
        String[]  ArrayX4=x4.split("_");

        String midPointfx1Andx2=((Double.parseDouble(ArrayX1[0])+Double.parseDouble((ArrayX2[0])))/2)
                +"_"+((Double.parseDouble(ArrayX1[1])+Double.parseDouble(ArrayX2[1]))/2);

        String midPointfx3Andx4=((Double.parseDouble(ArrayX3[0])+Double.parseDouble((ArrayX4[0])))/2)
                +"_"+((Double.parseDouble(ArrayX3[1])+Double.parseDouble(ArrayX4[1]))/2);

        String[]  ArrayX5=midPointfx1Andx2.split("_");
        String[]  ArrayX6=midPointfx3Andx4.split("_");

        String finalMid=(((Double.parseDouble(ArrayX5[0])+Double.parseDouble((ArrayX6[0]))))/2)
                +"_"+((Double.parseDouble(ArrayX5[1])+Double.parseDouble(ArrayX6[1]))/2);


        midValues.add(finalMid);
        System.out.println(finalMid);
    }
    private static void printTeamNames(String[][] teamNames) {
        System.out.println("team names");
        for(String[] strings:teamNames)
        {
            for(String j:strings)
            {
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }


    private int[][] Dpcalculation(int[][] arr) {

        int[][] ans = new int[arr.length + 1][arr[0].length + 1];
        for (int x = 1; x < arr.length + 1; x++) {
            for (int j = 1; j < arr[0].length + 1; j++) {

                if ((arr[x - 1][j - 1] == 1)&&ans[x][j-1]!=0 && ans[x-1][j]!=0) {
                    ans[x][j] = ans[x - 1][j] + ans[x][j - 1] - ans[x - 1][j - 1] + arr[x - 1][j - 1];
                }

                 else if((arr[x - 1][j - 1] == 1) && ans[x][j-1]!=0)
                {
                    ans[x][j]=ans[x][j-1]+1;
                }

                 else if((arr[x - 1][j - 1] == 1) && ans[x-1][j]!=0)
                {
                    ans[x][j]=ans[x-1][j]+1;
                }

                 else if(arr[x-1][j-1]==1)
                {
                    ans[x][j]=arr[x-1][j-1];
                }
                System.out.print(ans[x][j] + " ");
            }
            System.out.println();

        }
        return ans;
    }

    private String getValueByKey(LinkedHashMap<String, Integer> map, Integer integer) {

            for(Map.Entry<String,Integer>entry:map.entrySet())
            {
                if(entry.getValue().equals(integer))
                    return entry.getKey();
            }
            return null;
        }

    public static  String nearCluster(String point1,String point2)
    {
        String midvalue=FindAvgMidOfTheCluster();

        String[] arrayA = midvalue.split("_");

        String[] arrayX = point1.split("_");

        String[] arrayY = point2.split("_");

        Double diffOfAX = Math.abs(Double.parseDouble(arrayA[0]) - Double.parseDouble(arrayX[0]))
                + Math.abs(Double.parseDouble(arrayA[1]) - Double.parseDouble((arrayX[1])));

        Double diffOfAY = Math.abs(Double.parseDouble(arrayA[0]) - Double.parseDouble(arrayY[0]))
                + Math.abs(Double.parseDouble(arrayA[1]) - Double.parseDouble((arrayY[1])));


        if(diffOfAX.equals(diffOfAY))
        {
            if(dp[Integer.parseInt(arrayX[0])][Integer.parseInt(arrayX[1])]>=
                    dp[Integer.parseInt(arrayY[0])][Integer.parseInt(arrayY[1])])
            {
                return point1;
            }
            else
                return point2;
        }

        if (diffOfAX > diffOfAY)
            return point2;

        else
            return point1;

    }
    private static String FindAvgMidOfTheCluster() {
        String avgMid="";

        Double xPart = 0D;
        Double yPart = 0D;

        int count=0;
        for(String string:midValues)
        {
            String arr[]=string.split("_");
            xPart=xPart+(Double.parseDouble(arr[0]));
            yPart=yPart+(Double.parseDouble(arr[1]));
            count++;

        }

        avgMid=(xPart/count)+"_"+(yPart/count);

        return avgMid;
    }

}

