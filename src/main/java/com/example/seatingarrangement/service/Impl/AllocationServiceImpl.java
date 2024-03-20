package com.example.seatingarrangement.service.Impl;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.*;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.Service.AllocationRepoService;
import com.example.seatingarrangement.repository.Service.CompanyRepoService;
import com.example.seatingarrangement.repository.TeamRepository;
import com.example.seatingarrangement.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {
    public static int[][] arr;
    public static Integer pref = 0;
    public static int[][] dp;
    public static int availableSpaces = 0;
    public static String[][] teamNames;
    public static LinkedHashMap<String, Character> tempTeamList = new LinkedHashMap<>();
    public static List<UserReferenceDto.TeamReference> tempTeamList2 = new ArrayList<>();
    public static LinkedHashMap<String, Character> tempTeamList1 = new LinkedHashMap<>();
    public static ArrayList<String> midValues = new ArrayList<>();
    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private AllocationRepoService allocationRepoService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CompanyRepoService companyRepoService;

    @Autowired
    private ModelMapper modelMapper;

    private static void changingTrueToFalse(boolean[][] booleans, int[][] dp, int column, int row, String key, int value) {
        int rowStartingValue;
        boolean rowStartingFlag;
        System.out.println(key + " " + value);
        int copyOfValue2 = value;
        String x4 = (row + 1) + "_" + (1 + column);
        String x2 = x4;
        String x1 = x4;
        String x3 = x4;
        int x3Flg = 0;
        int x2Flg;
        int copyOfValue = value;
        int comeOut = 0;
        int countOf1 = 0;
        int firstValueInRow = -1;
        System.out.println("changing" + column + row);
        for (int i = row; i >= 0; i--) {
            x2Flg = 0;
            rowStartingValue = -1;
            rowStartingFlag = false;
            if (i != row && countOf1 == 0) {
                break;
            }
            countOf1 = 0;
            for (int j = column; j >= 0; j--) {
                if (!rowStartingFlag && rowStartingValue == -1 && dp[i + 1][j + 1] != 0) {
                    rowStartingValue = j;
                    rowStartingFlag = true;
                }
                if (firstValueInRow == -1 && dp[i + 1][j + 1] == 0) {
                    x3Flg = 1;
                    firstValueInRow = j + 1;
                    if (row == i) break;
                }
                if (dp[i + 1][j + 1] == 0 && j <= firstValueInRow) {
//                    if (j == firstValueInRow) {
                    firstValueInRow = j + 1;
                    break;
//                    } else if (j < firstValueInRow) {
//                        firstValueInRow = j + 1;
//
//                        break;
//                    }
                }
                if (dp[i + 1][j + 1] == 0) {
                    x3Flg = 1;
                    continue;
                }
                countOf1++;
                copyOfValue--;
                if (copyOfValue == 0) {
                    comeOut = 1;
                }
                booleans[i][j] = false;
                arr[i][j] = 0;
                x1 = (i + 1) + "_" + (j + 1);
                if (x3Flg == 0) {
                    x3 = (i + 1) + "_" + (j + 1);
                }
                if (x2Flg == 0) {
                    x2 = (i + 1) + "_" + (j + 1);
                    x2Flg = 1;
                }
                teamNames[i][j] = tempTeamList.get(key) + "" + copyOfValue2;
                firstValueInRow = j;
                copyOfValue2--;
                System.out.println(dp[i + 1][j + 1]);
            }
            column = rowStartingValue;
            if (firstValueInRow == -1) {
                firstValueInRow = 0;
            }
            if (comeOut == 1) break;
        }
        printTeamNames(teamNames);
        calculateMidPt(x1, x2, x3, x4);
        System.out.println(x1 + " " + x2 + " " + x3 + " " + x4);
    }

    public static void calculateMidPt(String x1, String x2, String x3, String x4) {
        String[] ArrayX1 = x1.split("_");
        String[] ArrayX2 = x2.split("_");
        String[] ArrayX3 = x3.split("_");
        String[] ArrayX4 = x4.split("_");
        String midPointfx1Andx2 = ((Double.parseDouble(ArrayX1[0]) + Double.parseDouble((ArrayX2[0]))) / 2) + "_" + ((Double.parseDouble(ArrayX1[1]) + Double.parseDouble(ArrayX2[1])) / 2);
        String midPointfx3Andx4 = ((Double.parseDouble(ArrayX3[0]) + Double.parseDouble((ArrayX4[0]))) / 2) + "_" + ((Double.parseDouble(ArrayX3[1]) + Double.parseDouble(ArrayX4[1])) / 2);
        String[] ArrayX5 = midPointfx1Andx2.split("_");
        String[] ArrayX6 = midPointfx3Andx4.split("_");
        String finalMid = ((Double.parseDouble(ArrayX5[0]) + Double.parseDouble((ArrayX6[0]))) / 2) + "_" + ((Double.parseDouble(ArrayX5[1]) + Double.parseDouble(ArrayX6[1])) / 2);
        midValues.add(finalMid);
        System.out.println(finalMid);
    }

    private static void printTeamNames(String[][] teamNames) {
        System.out.println("team names");
        for (String[] strings : teamNames) {
            for (String j : strings) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public static String nearCluster(String point1, String point2) {
        String midvalue = FindAvgMidOfTheCluster();
        String[] arrayA = midvalue.split("_");
        String[] arrayX = point1.split("_");
        String[] arrayY = point2.split("_");
        Double diffOfAX = Math.abs(Double.parseDouble(arrayA[0]) - Double.parseDouble(arrayX[0])) + Math.abs(Double.parseDouble(arrayA[1]) - Double.parseDouble((arrayX[1])));
        Double diffOfAY = Math.abs(Double.parseDouble(arrayA[0]) - Double.parseDouble(arrayY[0])) + Math.abs(Double.parseDouble(arrayA[1]) - Double.parseDouble((arrayY[1])));
        if (diffOfAX.equals(diffOfAY)) {
            if (dp[Integer.parseInt(arrayX[0])][Integer.parseInt(arrayX[1])] >= dp[Integer.parseInt(arrayY[0])][Integer.parseInt(arrayY[1])]) {
                return point1;
            } else return point2;
        }
        if (diffOfAX > diffOfAY) return point2;
        else return point1;
    }

    private static String FindAvgMidOfTheCluster() {
        String avgMid;
        double xPart = 0D;
        double yPart = 0D;
        int count = 0;
        for (String string : midValues) {
            String[] arr = string.split("_");
            xPart = xPart + (Double.parseDouble(arr[0]));
            yPart = yPart + (Double.parseDouble(arr[1]));
            count++;
        }
        avgMid = (xPart / count) + "_" + (yPart / count);
        return avgMid;
    }

    @Override
    public ResponseEntity<ResponseDto> add(LayoutDto layoutDto) {
        Optional<Company> company=companyRepository.findById(layoutDto.getCompanyId());
        LayoutDto responseLayoutDto=new LayoutDto();
//        if(company.isEmpty())
//            throw new BadRequestException("company not present");
//        if(layoutDto.getLayoutId()==null&&layoutDto.getDefaultLayout()==null)
//            throw new BadRequestException("data not present");
         if(layoutDto.getLayoutId()==null){
            Company.DefaultLayout defaultLayout=new Company.DefaultLayout();
            defaultLayout.setCompanylayout(layoutDto.getDefaultLayout());
            defaultLayout.setLayoutId(UUID.randomUUID().toString());
            defaultLayout.setTotalSpace(findTotalSpace(layoutDto.getDefaultLayout()));
            modelMapper.map(defaultLayout,responseLayoutDto);
            company.get().getCompanyLayout().add(defaultLayout);
//            companyRepository.save(company.get());
        } else if (layoutDto.getDefaultLayout()==null) {
            int ind=0;
            for (Company.DefaultLayout defaultLayout:company.get().getCompanyLayout()){
                if(defaultLayout.getLayoutId().equals(layoutDto.getLayoutId())){
                    company.get().getCompanyLayout().remove(ind);
                    break;
                }
                ind++;
            }
            System.out.println(company.get().getCompanyLayout());
            company.get().setCompanyLayout(company.get().getCompanyLayout());
//            companyRepository.save(company.get());
        }
        else{
            int ind=0;
            for (Company.DefaultLayout defaultLayout:company.get().getCompanyLayout()){
                if(defaultLayout.getLayoutId().equals(layoutDto.getLayoutId())){
                    defaultLayout.setCompanyLayout(layoutDto.getDefaultLayout());
                    defaultLayout.setTotalSpace(availableSpacesCount(layoutDto.getLayOut()));
                    company.get().getCompanyLayout().set(ind,defaultLayout);
                    modelMapper.map(defaultLayout,responseLayoutDto);
                    break;
                }
                ind++;
            }
//            companyRepository.save(company.get());
        }
        responseLayoutDto.setCompanyId(layoutDto.getCompanyId());
        companyRepository.save(company.get());
//        return responseLayoutDto;

//        log.info(layoutDto.toString());
//        Company company = new Company();
//        company.setCompanyName(layoutDto.getCompanyName());
//        layout.setLayOut(layoutDto.getLayOut());
//        layout.setTeamIdList(new LinkedHashMap<>());
//        availableSpaces = availableSpacesCount(layout.getLayOut());
//        layout.setAvailableSpaces(availableSpaces);
//        companyRepoService.insert(layout);
//        log.info(layout.toString());
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(layout.getLayOut(), "layout saved", HttpStatus.OK));
    return null;
    }

    @Override
    public ResponseEntity<ResponseDto> addAllocation(AllocationDto allocationDto) {
        String layoutId = allocationDto.getLayoutId();
        pref = allocationDto.getPreference();
        GetLayoutDto getLayoutDto = companyRepoService.findByLayoutId(layoutId);
        LinkedHashMap<String, Character> teamList = new LinkedHashMap<>();
        int spacesTobeAllocated = 0;
        HashMap<String, Integer> map = allocationDto.getToBeAllocated();  //ch
        System.out.println(getLayoutDto.toString());
        for (int i : map.values()) {
            spacesTobeAllocated += i;
        }
        availableSpaces = getLayoutDto.getAvailableSpaces();
        System.out.println(spacesTobeAllocated + " " + availableSpaces);
        if (availableSpaces < spacesTobeAllocated)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("", "members exceeds the spaces", HttpStatus.OK));
        List<TeamInfo> teamInfos = new ArrayList<>();
        TeamInfo teamInfo = new TeamInfo();
        LinkedHashMap<String, Character> tempList = new LinkedHashMap<>();
        for (String name : map.keySet()) {
            if (tempList.isEmpty()) {
                tempList.put(name, 'A');
                teamInfo.setTeamCode("A");

            } else {
                teamList = tempList;
                char lastId = teamList.values().stream().toList().get(teamList.size() - 1);
                ++lastId;
                tempList.put(name, lastId);
                teamInfo.setTeamCode("" + lastId);
            }
            teamInfo.setTeamName(name);
            teamInfo.setTeamCount(map.get(name));
            teamInfos.add(teamInfo);


        }
        tempTeamList = tempList;
        SeatingCalculationDto seatingCalculationDto = new SeatingCalculationDto(getLayoutDto.getLayout(), teamList, map);
        seatingCalculation(seatingCalculationDto);
        log.info(tempList.toString());
        Allocation allocation = new Allocation();
        allocation.setDefaultLayoutId(layoutId);

        if (pref == 1)
            allocation.setAllocationType(Type.ASC);
        else if (pref == 2)
            allocation.setAllocationType(Type.DESC);
        else
            allocation.setAllocationType(Type.RANDOM);

        allocation.setAllocationLayout(teamNames);

        Team team = new Team();
        team.setTeams(teamInfos);
        team.setLayoutId(layoutId);
        teamRepository.save(team);


//        Allocation allocation = new Allocation(layOut.get().getId(), layOut.get().getCompanyName(), teamNames);
        UserReferenceDto userReferenceDto = new UserReferenceDto(teamNames, tempTeamList2);
        allocationRepository.save(allocation);
        System.out.println("teamList" + tempTeamList1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(userReferenceDto, "allocation created", HttpStatus.OK));
    }

    private void seatingCalculation(SeatingCalculationDto seatingCalculationDto) {
        arr = seatingCalculationDto.getLayOut();
        teamNames = new String[arr.length][arr[0].length];
        HashMap<String, Integer> map = seatingCalculationDto.getToBeAllocated();
        boolean[][] booleans = new boolean[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                booleans[i][j] = arr[i][j] == 1;
            }
        }
        List<Integer> al = new ArrayList<>(map.values().stream().toList());
        System.out.println(map);
        if (pref == 1 || pref == 2)
            Collections.sort(al);
        if (pref == 2)
            Collections.reverse(al);
        if (pref == 3) {
            Collections.shuffle(al);
        }
        tempTeamList2 = new ArrayList<>();
        for (int i = al.size() - 1; i >= 0; i--) {
            midValues.clear();
            String key = getValueByKey(map, al.get(i));
            tempTeamList1.put(key, tempTeamList.get(key));
            UserReferenceDto.TeamReference teamReference = new UserReferenceDto.TeamReference(key, tempTeamList.get(key));
            tempTeamList2.add(teamReference);
            dp = dpCalculation(arr);
            ArrayList<String> arrayList = new ArrayList<>();
            while (al.get(i) > 0) {
                int m = 0;
                int firstIncomingFlag = 0;
                dp = dpCalculation(arr);
                int value = al.get(i);
                int h = 1;
                int g;
                int tempg = 0;
                int temph = 0;
                while (h < dp.length) {
                    g = 1;
                    while (g < dp[h].length) {
                        if (dp[h][g] != 0 && dp[h][g] <= value) {
                            if (dp[h][g] == value) {
                                if (arrayList.isEmpty()) {
                                    changingTrueToFalse(booleans, dp, g - 1, h - 1, key, value);
                                    arrayList.add(g + "_" + h);
                                    map.replace(key, 0);
                                    h = dp.length - 1;
                                    al.set(i, 0);
                                    m = 1;
                                    value = 0;
                                    break;
                                } else {
                                    if (firstIncomingFlag == 0) {
                                        tempg = g;
                                        temph = h;
                                        firstIncomingFlag = 1;
                                    }
                                    if (firstIncomingFlag == 1) {
                                        String nearValue = nearCluster(temph + "_" + tempg, h + "_" + g);
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
                                } else if (!arrayList.isEmpty()) {
                                    String nearValue = nearCluster(temph + "_" + tempg, h + "_" + g);
                                    String[] tempArray = nearValue.split("_");
                                    temph = Integer.parseInt(tempArray[0]);
                                    tempg = Integer.parseInt(tempArray[1]);
                                } else {
                                    if (dp[h][g] > dp[temph][tempg]) {
                                        temph = h;
                                        tempg = g;
                                    }
                                }
                            }
                        }
                        g++;
                    }
                    h++;
                }
                if (firstIncomingFlag == 1 && m == 0) {
                    changingTrueToFalse(booleans, dp, tempg - 1, temph - 1, key, value);
                    arrayList.add(tempg + "_" + temph);
                    map.replace(key, value - dp[temph][tempg]);
                    al.set(i, al.get(i) - dp[temph][tempg]);
                }
            }
        }
    }

    private int availableSpacesCount(int[][] layOut) {
        int total = 0;
        for (int[] value : layOut) {
            String m = Arrays.toString(value);
            String h = m.replace("1", "");
            total += m.length() - h.length();
        }
        return total;
    }

    @Override
    public ResponseEntity<ResponseDto> getLayOut(String companyName) {
        Optional<Company> company = companyRepository.findByCompanyName(companyName);
        return company.map(out -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(out, "layout obtained", HttpStatus.OK))).orElseGet(() -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("", "Company not found", HttpStatus.OK)));
    }

    private int[][] dpCalculation(int[][] arr) {
        int[][] ans = new int[arr.length + 1][arr[0].length + 1];
        for (int x = 1; x < arr.length + 1; x++) {
            for (int j = 1; j < arr[0].length + 1; j++) {
                if ((arr[x - 1][j - 1] == 1) && ans[x][j - 1] != 0 && ans[x - 1][j] != 0) {
                    ans[x][j] = ans[x - 1][j] + ans[x][j - 1] - ans[x - 1][j - 1] + arr[x - 1][j - 1];
                } else if ((arr[x - 1][j - 1] == 1) && ans[x][j - 1] != 0) {
                    ans[x][j] = ans[x][j - 1] + 1;
                } else if ((arr[x - 1][j - 1] == 1) && ans[x - 1][j] != 0) {
                    ans[x][j] = ans[x - 1][j] + 1;
                } else if (arr[x - 1][j - 1] == 1)
                    ans[x][j] = arr[x - 1][j - 1];
                System.out.print(ans[x][j] + " ");
            }
        }
        return ans;
    }

    @Override
    public CsvOutputDto convertCsvFile(InputStream inputStream) throws IOException {
        CsvOutputDto csvOutputDto = new CsvOutputDto();
        Integer spacesOccupied = 0;
        boolean flag = true;
        List<TeamDto> teamDtoList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        int noOfColumns = 0;
        int noOfRows = 0;
        for (Row row : sheet) {
            if (noOfRows == 0) noOfColumns = row.getPhysicalNumberOfCells();
            else {
                TeamDto teamDto = new TeamDto();
                for (int i = 0; i < noOfColumns; i++) {
                    if (row.getCell(i) != null) {
                        if (i == 0) teamDto.setTeamName(row.getCell(i).toString());
                        else {
                            teamDto.setTeamCount(Integer.valueOf(String.valueOf(row.getCell(i)).split("\\.")[0]));
                            spacesOccupied += teamDto.getTeamCount();
                        }
                    } else {
                        flag = false;
                        break;
                    }
                }
                teamDtoList.add(teamDto);
            }
            ++noOfRows;
        }
        csvOutputDto.setTeamDtoList(teamDtoList);
        csvOutputDto.setFlag(flag);
        csvOutputDto.setSpacesOccupied(spacesOccupied);
        return csvOutputDto;
    }

    private String getValueByKey(HashMap<String, Integer> map, Integer integer) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(integer)) return entry.getKey();
        }
        return null;
    }
}