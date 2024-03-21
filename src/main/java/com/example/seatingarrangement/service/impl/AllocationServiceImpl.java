package com.example.seatingarrangement.service.impl;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.*;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.service.AllocationRepoService;
import com.example.seatingarrangement.repository.service.CompanyRepoService;
import com.example.seatingarrangement.repository.TeamRepository;
import com.example.seatingarrangement.repository.service.TeamRepoService;
import com.example.seatingarrangement.service.AllocationAbstract;
import com.example.seatingarrangement.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private AllocationRepoService allocationRepoService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamRepoService teamRepoService;

    @Autowired
    private CompanyRepoService companyRepoService;

    @Autowired
    private ModelMapper modelMapper;






    @Override
    public ResponseEntity<ResponseDto> add(CompanyDto companyDto) throws BadRequestException {
        Company company = new Company();
        company.setCompanyId(UUID.randomUUID().toString());
        company.setCompanyName(companyDto.getCompanyName());
        List<Company.DefaultLayout> defaultLayoutList = new ArrayList<>();

        for (int[][] layout : companyDto.getCompanyLayoutList()) {
            Company.DefaultLayout defaultLayout = new Company.DefaultLayout();
            defaultLayout.setLayoutId(UUID.randomUUID().toString());
            defaultLayout.setCompanyLayout(layout);
            defaultLayout.setTotalSpace(availableSpacesCount(layout));
            defaultLayoutList.add(defaultLayout);
        }
        company.setCompanyLayout(defaultLayoutList);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(companyRepository.save(company),"company saved",HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ResponseDto> addAllocation(TeamObjectDto teamObjectDto) throws BadRequestException {
        AllocationAbstract allocationAbstract;
          if(teamObjectDto.getAlgorithmPref()==1)
          {
              GreedyImpl greedyImpl=new GreedyImpl(teamRepoService,companyRepoService,teamRepository,allocationRepoService,allocationRepository,modelMapper);

              System.out.println("h       i          "+companyRepository.findByLayoutId(teamObjectDto.getLayoutId()));
            return greedyImpl.createAllocation(teamObjectDto);

          }
          else if(teamObjectDto.getAlgorithmPref()==2)
          {
              BacktrackingImpl backtracking=new BacktrackingImpl(teamRepoService,companyRepoService,teamRepository,allocationRepoService,allocationRepository,modelMapper);

              System.out.println("BTTTTTTTTTT");
           return   backtracking.createAllocation(teamObjectDto);
          }

          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("","incorrect choice",HttpStatus.BAD_REQUEST));
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
    public ResponseEntity<ResponseDto> getAllLayOut(String companyName) {

        Optional<Company> company = companyRepository.findByCompanyName(companyName);

        //queryChange
        return company.map(out -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(out, "layout obtained", HttpStatus.OK))).orElseGet(() -> ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("", "Company not found", HttpStatus.OK)));
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

    @Override
    public ResponseEntity<ResponseDto> updateLayout(LayoutDto layoutDto) throws BadRequestException {
        LayoutDto responseLayoutDto=new LayoutDto();
        Company company=isValid(layoutDto);
        if(layoutDto.getLayoutId()==null){
            Company.DefaultLayout defaultLayout=new Company.DefaultLayout();
            defaultLayout.setCompanyLayout(layoutDto.getDefaultLayout());
            defaultLayout.setLayoutId(UUID.randomUUID().toString());
            defaultLayout.setTotalSpace(availableSpacesCount(layoutDto.getDefaultLayout()));
            modelMapper.map(defaultLayout,responseLayoutDto);
            company.getCompanyLayout().add(defaultLayout);
        } else if (layoutDto.getDefaultLayout()==null) {
            int ind=0;
            for (Company.DefaultLayout defaultLayout:company.getCompanyLayout()){
                if(defaultLayout.getLayoutId().equals(layoutDto.getLayoutId())){
                    company.getCompanyLayout().remove(ind);
                    break;
                }
                ind++;
            }
            company.setCompanyLayout(company.getCompanyLayout());
        }
        else{
            int ind=0;
            for (Company.DefaultLayout defaultLayout:company.getCompanyLayout()){
                if(defaultLayout.getLayoutId().equals(layoutDto.getLayoutId())){
                    defaultLayout.setCompanyLayout(layoutDto.getDefaultLayout());
                    defaultLayout.setTotalSpace(availableSpacesCount(layoutDto.getDefaultLayout()));
                    company.getCompanyLayout().set(ind,defaultLayout);
                    modelMapper.map(defaultLayout,responseLayoutDto);
                    break;
                }
                ind++;
            }
        }
        responseLayoutDto.setCompanyName(layoutDto.getCompanyName());
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseLayoutDto,"updates done",HttpStatus.OK));

    }


    Company isValid(LayoutDto layoutDto) throws BadRequestException {
        Optional<Company> company=companyRepository.findByCompanyName(layoutDto.getCompanyName());
        if(company.isEmpty())
            throw new BadRequestException("company not present");
        if(layoutDto.getLayoutId()==null && layoutDto.getDefaultLayout()==null)
            throw new BadRequestException("data not present");
        if(layoutDto.getLayoutId()!=null&&companyRepoService.findByLayoutId(layoutDto.getLayoutId())==null)
            throw new BadRequestException("layoutId not present");
        return company.get();
    }

}