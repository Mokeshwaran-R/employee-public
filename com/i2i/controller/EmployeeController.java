package com.i2i.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.i2i.constants.Constants;
import com.i2i.dto.EmployeeDto;
import com.i2i.enums.Gender;
import com.i2i.exceptions.EmployeeException;
import com.i2i.mapper.EmployeeMapper;
import com.i2i.service.EmployeeService;
import com.i2i.service.impl.EmployeeServiceImpl;
import com.i2i.util.ValidationUtil;


/**
 * <p>
 * EmployeeController gets input and displays output
 * based on CRUD operations.
 * </p>
 *
 * @author Mokeshwaran
 * @version 1.0 2022-08-10
 * 
 */
public class EmployeeController {

    EmployeeService employeeService = new EmployeeServiceImpl();
    private static Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * <p>
     * This is the main method of the program
     * <p>
     *
     * @param String[] args
     */
    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();
        Scanner scanner = new Scanner(System.in);
        String choiceString;
        int choice = 0;
        String optionString;
        int option = 0;
        boolean isValidInput = false;
        do {
            try {
                logger.info("\nChoose any corresponding number to do that operation");
                logger.info("1. Trainee \n2. Trainer\n3. Exit");
                option = getOption();
                switch (option) {
                    case 1:
                        choice = traineeOperations();
                        switch (choice) {
                            case 1:
                                employeeController.addEmployee(option);
                                break;
                            case 2:
                                employeeController.displayAllTrainees();
                                break;
                            case 3:    
                                employeeController.editTraineeById();
                                break;
                            case 4:
                                break;    // used as exit option without showing error message.
                            default:
                                logger.warn(Constants.INVALID_INPUT_MSG);
                                break;
                        }
                        break;
                    case 2:
                        choice = trainerOperations();
                        switch (choice) {
                            case 1:
                                employeeController.addEmployee(option);
                                break;
                            case 2: 
                                employeeController.displayAllTrainers();
                                break;
                            case 3:
                                employeeController.editTrainerById();
                                break;
                            case 4:
                                break;    // used as exit option without showing error message.
                            default:
                                logger.warn(Constants.INVALID_INPUT_MSG);
                                break;
                        }
                        isValidInput = false;
                        break;
                    case 3:
                        isValidInput = false;
                        break; 
                    default:
                        logger.warn(Constants.INVALID_INPUT_MSG);
                        isValidInput = true;
                        break;
                }
            } catch (EmployeeException employeeException) {
                logger.error(Constants.INVALID_FORMAT_MSG, employeeException.getMessage());
                isValidInput = true;
                scanner.nextLine();
            }
        } while (option > 0 && option < 3 || isValidInput);
    }

    /**
     * <p>
     * This method will add employee to the list based on trainee or trainer.
     * </p>
     * 
     * (e.g): ID: 172 (auto-assigned), Name: Sherlock Holmes, Gender (M/F): M,
     * Email ID: holmes@holmes.com, Designation: Trainer, Residential Address: 221B Baker Street,
     * Previous Experience: 6, Date Of Joining (dd-MM-yyyy): 12-12-2020,
     * Date Of Birth (dd-MM-yyyy): 10-08-1998.
     * 
     * @param int option
     * @throws EmployeeException
     */
    private void addEmployee(int option) throws EmployeeException {
        Scanner scanner = new Scanner(System.in);
        String name;
        String gender;
        Date dateOfBirth = null;
        String resiAddress;
        String emailId;
        Date dateOfJoining = null;
        int previousExperience;
        
        logger.info("\n*Required Field");
        name = getName();
        gender = getGender();
        dateOfBirth = getDateOfBirth();
        resiAddress = getResiAddress();
        emailId = getEmailId();   
        dateOfJoining = getDateOfJoining(dateOfBirth);
        previousExperience = getPreviousExperience(dateOfBirth);

        if (option == 1) {
            addTrainee(name, gender, dateOfBirth, resiAddress, emailId,
                       dateOfJoining, previousExperience);
        } else if (option == 2) {
            addTrainer(name, gender, dateOfBirth, resiAddress, emailId,
                       dateOfJoining, previousExperience);
        } else {
            logger.warn(Constants.INVALID_INPUT_MSG);
            throw new EmployeeException(Constants.INVALID_INPUT_MSG);
        }
    }
    
    /**
     * <p>
     * This method will add trainee to the employee db.
     * </p>
     * 
     * (e.g): Trainer's Name: Watson, Batch ID: 3,
     * Training Course: Java, Python, Dart
     * 
     * @param String name
     * @param String gender
     * @param Date dateOfBirth
     * @param String resiAddress
     * @param String emailId
     * @param Date dateOfJoining
     * @param int previousExperience
     * @throws EmployeeException
     */
    private void addTrainee(String name, String gender, Date dateOfBirth,
                            String resiAddress, String emailId,
                            Date dateOfJoining, int previousExperience)
                            throws EmployeeException {
        Scanner scanner = new Scanner(System.in);
        int id = 0;
        String trainersName;
        String batchId = null;
        String trainingCourse;
        String yesOrNo;
        
        trainersName = getTrainersName();
        trainingCourse = getTrainingCourse();
        yesOrNo = getYesOrNo();

        if ("y".equalsIgnoreCase(yesOrNo)) {
            try {
                id = employeeService.generateId();
                batchId = employeeService.generateBatchId();
            } catch (EmployeeException employeeException) {
                logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
                throw new EmployeeException(employeeException.getMessage());
            }
            logger.info("This is your ID & Batch ID remember it for future reference -\n");
            logger.info("ID: " + id);
            logger.info("Batch ID: " + batchId);
            EmployeeDto traineeDto = new EmployeeDto(id, name, gender, emailId,
                                                     resiAddress,
                                                     previousExperience,
                                                     dateOfBirth, dateOfJoining,
                                                     trainersName, batchId,
                                                     trainingCourse, 0, 0,
                                                     new HashSet<String>());
            try {
                employeeService.createNewTrainee(EmployeeMapper.dtoToEntity(traineeDto));
            } catch (EmployeeException employeeException) {
                logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
                throw new EmployeeException(employeeException.getMessage());
            }
        } else if ("n".equalsIgnoreCase(yesOrNo)) {
            logger.info("Session Terminated");
        } else {
            logger.warn("Invalid Key");
        }
    }

    /**
     * <p>
     * This method will display all trainees page wise
     * from the trainees list.
     * </p>
     * 
     * (e.g): Trainee1, Trainee2, Trainee3... and so on.
     */
    public void displayAllTrainees() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        int initialResult = 0;
        try {
            if (EmployeeMapper
                .entityToDtoList(employeeService
                                 .getAllTrainees(initialResult)).isEmpty()) {
                logger.info("There's No Existing Record");
            } else {
                while (initialResult >= 0) {
                    List<EmployeeDto> trainees =
                            EmployeeMapper.entityToDtoList(employeeService
                                                           .getAllTrainees(initialResult));
                    if (initialResult == 0) {
                        for (EmployeeDto traineeDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainees(initialResult))) {
                            logger.info(traineeDto);
                        }
                        logger.info("Press 1 for Next set of results, 2 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult += 5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    } else if (!(initialResult > 0 && trainees.size() % 5 == 0)) {
                        for (EmployeeDto traineeDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainees(initialResult))) {
                            logger.info(traineeDto);
                        }
                        logger.info("End of the page");
                        logger.info("Press 1 to go previous, 2 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult -= 5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    } else {
                        for (EmployeeDto traineeDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainees(initialResult))) {
                            logger.info(traineeDto);
                        }
                        logger.info("Press 1 for Next set of results, 2 to go previous, 3 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult += 5;
                                break;
                            case 2:
                                initialResult -=5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    }
                }
            }
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
        } 
    }
    
    /**
     * <p>
     * This method will display trainee using ID from the trainees list.
     * </p>
     * 
     */
    public void editTraineeById() {
        Scanner scanner = new Scanner(System.in);
        String name;
        String gender;
        Date dateOfBirth = null;
        String resiAddress;
        String emailId;
        Date dateOfJoining = null;
        int previousExperience = -1;
        String trainersName;
        String trainingCourse;
        String yesOrNo;
        boolean isValidInput;
        int id = 0;
        String batchId;

        try {
            logger.info("Enter Employee ID: ");
            id = scanner.nextInt();
            if (employeeService.checkTrainee(id)) {
                batchId = employeeService.getTraineeBatchId(id);

                logger.info(EmployeeMapper
                            .entityToDto(employeeService
                                         .searchTraineeById(id)));
                logger.info("What Do You Want To Do?");
                logger.info("1. Delete Trainee\n2. Update Trainee\n3. Exit Menu");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        employeeService.deleteTrainee(id);
                        logger.info("Trainee Deleted Successfully");
                        break;
                    case 2:
                        scanner.nextLine();
                        name = getNameUpdate();
                        gender = getGenderUpdate();
                        dateOfBirth = getTraineeDateOfBirthUpdate(id);
                        resiAddress = getResiAddressUpdate();
                        emailId = getEmailIdUpdate();
                        dateOfJoining = getTraineeDateOfJoiningUpdate(dateOfBirth, id);
                        previousExperience = getPreviousExperienceUpdate(dateOfBirth);
                        trainersName = getTrainersNameUpdate();
                        trainingCourse = getTrainingCourseUpdate();
                        yesOrNo = getYesOrNo();
                            
                        EmployeeDto traineeDto = new EmployeeDto(id, name, gender,
                                                                 emailId,
                                                                 resiAddress,
                                                                 previousExperience,
                                                                 dateOfBirth,
                                                                 dateOfJoining,
                                                                 trainersName,
                                                                 batchId,
                                                                 trainingCourse,
                                                                 0, 0,
                                                                 new HashSet<String>());
                        if ("y".equalsIgnoreCase(yesOrNo)) {
                            employeeService
                            .updateTrainee(EmployeeMapper
                                           .dtoToEntity(traineeDto));
                        } else if ("n".equalsIgnoreCase(yesOrNo)) {
                            logger.info("Session Terminated");
                        } else {
                            logger.warn("Invalid Key");
                        }
                        break;  
                    case 3: 
                        break;                  
                    default:
                        break;        
                }
            } else {
                logger.info("There's No Trainee In This ID");
            }
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
        }
    }

    /**
     * <p>
     * This method will add trainer to the employee db.
     * </p>
     * 
     * (e.g): Trainee Count: 10, Training Experience: 3,
     * Preferred Skill for Training (use commas to separate e.g: java,python ): Java,Python,Dart
     * 
     * @param String name
     * @param String gender
     * @param Date dateOfBirth
     * @param String resiAddress
     * @param String emailId
     * @param Date dateOfJoining
     * @param int previousExperience
     * @throws EmployeeException
     */
    private void addTrainer(String name, String gender, Date dateOfBirth, String resiAddress,
        String emailId, Date dateOfJoining, int previousExperience) throws EmployeeException {
        Scanner scanner = new Scanner(System.in);
        int traineeCount = 0;
        int trainingExperience = 0;
        Set<String> prefTrainingSkill;
        String yesOrNo;
        int id = 0;
        
        try {
            traineeCount = getTraineeCount();
            trainingExperience = getTrainingExperience(dateOfBirth);
            prefTrainingSkill = getPrefTrainingSkill();
            yesOrNo = getYesOrNo();
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
            throw new EmployeeException(employeeException.getMessage());
        }

        if ("y".equalsIgnoreCase(yesOrNo)) {
            try {
                id = employeeService.generateId();
            } catch (EmployeeException employeeException) {
                logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
            }
            logger.info("This is your ID remember it for future reference: " + id + "\n");

            EmployeeDto EmployeeDto = new EmployeeDto(id, name, gender, emailId,
                                                      resiAddress,
                                                      previousExperience,
                                                      dateOfBirth, dateOfJoining,
                                                      "", "", "", traineeCount,
                                                      trainingExperience,
                                                      prefTrainingSkill);
            try {
                employeeService.createNewTrainer(EmployeeMapper
                                                 .dtoToEntity(EmployeeDto));
            } catch (EmployeeException employeeException) {
                logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
            }
            
        } else if ("n".equalsIgnoreCase(yesOrNo)) {
            logger.info("Session Terminated");
        } else {
            logger.warn("Invalid Key");
        }
    }

    /**
     * <p>
     * This method will display all trainers from the trainers list.
     * </p>
     * 
     */
    public void displayAllTrainers() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        int initialResult = 0;
        try {
            if (EmployeeMapper
                .entityToDtoList(employeeService
                                 .getAllTrainers(initialResult)).isEmpty()) {
                logger.info("There's No Existing Record");
            } else {
                while (initialResult >= 0) {
                    List<EmployeeDto> trainers = EmployeeMapper.entityToDtoList(employeeService.getAllTrainers(initialResult));
                    if (initialResult == 0) {
                        for (EmployeeDto trainerDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainers(initialResult))) {
                            logger.info(trainerDto);
                        }
                        logger.info("Press 1 for Next set of results, 2 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult += 5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    } else if ((initialResult > 0 && trainers.size() % 5 == 0)) {
                        for (EmployeeDto trainerDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainers(initialResult))) {
                            logger.info(trainerDto);
                        }
                        logger.info("End of the page");
                        logger.info("Press 1 to go previous, 2 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult -= 5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    } else {
                        for (EmployeeDto trainerDto : EmployeeMapper
                             .entityToDtoList(employeeService
                                              .getAllTrainers(initialResult))) {
                            logger.info(trainerDto);
                        }
                        logger.info("Press 1 for Next set of results, 2 to go previous, 3 to exit");
                        option = scanner.nextInt();
                        switch (option) {
                            case 1:
                                initialResult += 5;
                                break;
                            case 2:
                                initialResult -=5;
                                break;
                            default:
                                initialResult = -1;
                                break;
                        }
                    }
                }
            }
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
        }
    }

    /**
     * <p>
     * This method will display trainer using ID from the trainers list.
     * </p>
     * 
     */
    public void editTrainerById() {
        Scanner scanner = new Scanner(System.in);
        String name;
        String gender = "";
        Date dateOfBirth = null;
        String resiAddress;
        String emailId;
        Date dateOfJoining = null;
        int previousExperience = -1;
        int traineeCount = 0;
        int trainingExperience = -1;
        int option = 0;
        Set<String> prefTrainingSkill = null;
        String yesOrNo;
        boolean isValidInput;
        
        try {
            logger.info("Enter Employee ID: ");
            int id = scanner.nextInt();
            if (employeeService.checkTrainer(id)) {
                logger.info(EmployeeMapper
                            .entityToDto(employeeService
                                         .searchTrainerById(id)));
                logger.info("What Do You Want To Do?");
                logger.info("1. Delete Trainer\n2. Update Trainer\n3. Exit Menu");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        String isTransactedMsg = employeeService.deleteTrainer(id)
                                                 ? "Trainer Deleted Successfully"
                                                 : "Something Went Wrong";
                        logger.info(isTransactedMsg);
                        break;
                    case 2:
                        scanner.nextLine();
                        name = getNameUpdate();
                        gender = getGenderUpdate();
                        dateOfBirth = getTrainerDateOfBirthUpdate(id);
                        resiAddress = getResiAddressUpdate();
                        emailId = getEmailIdUpdate();
                        dateOfJoining = getTrainerDateOfJoiningUpdate(dateOfBirth, id);
                        previousExperience = getPreviousExperienceUpdate(dateOfBirth);
                        traineeCount = getTraineeCountUpdate();
                        trainingExperience = getTrainingExperienceUpdate(dateOfBirth);
                        prefTrainingSkill = getPrefTrainingSkillUpdate(id);
                        yesOrNo = getYesOrNo();

                        EmployeeDto EmployeeDto = new EmployeeDto(id, name,
                                                                  gender,
                                                                  emailId,
                                                                  resiAddress,
                                                                  previousExperience,
                                                                  dateOfBirth,
                                                                  dateOfJoining,
                                                                  "", "", "",
                                                                  traineeCount,
                                                                  trainingExperience,
                                                                  prefTrainingSkill);
                        if ("y".equalsIgnoreCase(yesOrNo)) {
                            employeeService.updateTrainer(EmployeeMapper.dtoToEntity(EmployeeDto));
                        } else if ("n".equalsIgnoreCase(yesOrNo)) {
                            logger.info("Session Terminated");
                        } else {
                            logger.warn("Invalid Key");
                        }
                        break;
                    case 3: 
                        break;    
                    default:
                        logger.warn(Constants.INVALID_INPUT_MSG);
                        break;
                }
            } else {
                logger.info("There's No Existing Record For This ID");
            }
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
        }        
    }

    /**
     * <p>
     * This method will get the options from the user and store it in options.
     * </p>
     * 
     * (e.g): option = 2
     * 
     * @return int option
     */
    private static int getOption() {
        Scanner scanner = new Scanner(System.in);
        String optionString;
        boolean isValidInput;
        int option = 0;
        optionString = scanner.nextLine();
        isValidInput = ValidationUtil.isValidNumber(optionString);
        if (isValidInput) {
            option = Integer.parseInt(optionString);
        }
        return option;
    }

    /**
     * <p>
     * This method will have all the operations for trainees.
     * </p>
     * 
     * @return int choice
     */
    private static int traineeOperations() {
        Scanner scanner = new Scanner(System.in);
        String choiceString;
        int choice = 0;
        boolean isValidInput;
        logger.info("\nChoose any corresponding number to do that operation");
        logger.info("1. Add Trainee" +
                        "\n2. Display All Trainees" + 
                        "\n3. Edit Trainee By ID" +
                        "\n4. Exit Menu");
        choiceString = scanner.nextLine();
        isValidInput = ValidationUtil.isValidNumber(choiceString);
        if (isValidInput) {
            choice = Integer.parseInt(choiceString);
        }
        return choice;
    }

    /**
     * <p>
     * This method will have all the operations for trainers.
     * </p>
     * 
     * @return int choice
     */
    private static int trainerOperations() {
        Scanner scanner = new Scanner(System.in);
        String choiceString;
        int choice = 0;
        boolean isValidInput;
        logger.info("\nChoose any corresponding number to do that operation");
        logger.info("1. Add Trainer" +
                            "\n2. Display All Trainers" +
                            "\n3. Edit Trainer by ID" +
                            "\n4. Exit Menu");
        choiceString = scanner.nextLine();
        isValidInput =  ValidationUtil.isValidNumber(choiceString);
        if (isValidInput) {
            choice = Integer.parseInt(choiceString);
        }
        return choice;
    }

    /**
     * <p>
     * This method will get the name from the user.
     * </p>
     *
     * (e.g): name = "Holmes"
     *
     * @return String name
     */
    private String getName() {
        Scanner scanner = new Scanner(System.in);
        String name;
        boolean isValidInput;
        do {
            logger.info("Name*: ");
            name = scanner.nextLine();
            isValidInput = ValidationUtil.isValidString(name);
            if (!isValidInput) {
                logger.warn("Name must not contain numbers, symbol, extra spaces or be empty");
            }
        } while (!isValidInput);
        return name;
    }

    /**
     * <p>
     * This method will get the gender from the user.
     * </p>
     *
     * (e.g): gender = 1 (male)
     *
     * @return String gender
     */
    private String getGender() {
        Scanner scanner = new Scanner(System.in);
        int genderOption = 0;
        String genderOptionString;
        String gender = null;
        boolean isValidInput;
        do {
            logger.info("Please Choose A Gender*" +
                               "\n1. Male" + 
                               "\n2. Female" +
                               "\n3. Others");
            genderOptionString = scanner.nextLine();
            isValidInput = ValidationUtil.isValidNumber(genderOptionString);
            if (isValidInput) {
                genderOption = Integer.parseInt(genderOptionString);
                switch (genderOption) {
                    case 1:
                        gender = Gender.MALE.name();
                        break;
                    case 2:
                        gender = Gender.FEMALE.name();
                        break;
                    case 3:
                        gender = Gender.OTHERS.name();
                        break;
                    default:
                        logger.warn(Constants.INVALID_INPUT_MSG);
                        break;
                }
            } else {
                logger.warn(Constants.INVALID_INPUT_MSG);
            }
        } while (genderOption < 1 || genderOption > 3 && isValidInput);
        return gender;
    }

    /**
     * <p>
     * This method will get the date of birth from the user.
     * </p>
     *
     * (e.g): dateOfBirth = 10-08-1998
     *
     * @return Date dateOfBirth
     */
    private Date getDateOfBirth() {
        Scanner scanner = new Scanner(System.in);
        String birthDate;
        boolean isValidInput;
        int birthYear = 0;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date dateOfBirth = null;
        do {
            logger.info("Date Of Birth (dd-MM-yyyy)*: ");
            birthDate = scanner.next();
            isValidInput = ValidationUtil.isValidDate(birthDate);
            if (!isValidInput) {
                logger.warn(Constants.INVALID_FORMAT_MSG);
                isValidInput = false;
            } else {
                birthYear = Integer.parseInt(birthDate.substring(6, 10));
                if (currentYear - birthYear >= Constants.MIN_AGE
                    && currentYear - birthYear <= Constants.MAX_AGE) {   
                    if (!isValidInput) {
                        logger.warn(Constants.INVALID_FORMAT_MSG);  
                        isValidInput = false;
                    } else {
                        try {
                            dateOfBirth =
                                    new SimpleDateFormat(Constants.DATE_FORMAT).parse(birthDate);
                        } catch (ParseException parseException) {
                            logger.warn(Constants.INVALID_FORMAT_MSG, parseException.getMessage());
                        }
                    }
                } else {
                    logger.warn("Age must be between 18 and 85");
                    isValidInput = false;
                }
            }
        } while (!isValidInput);
        return dateOfBirth;
    }

    /**
     * <p>
     * This method will get the residential address from the user.
     * </p>
     *
     * (e.g): resiAddress = "some address that goes here"
     *
     * @return String resiAddress
     */
    private String getResiAddress() {
        Scanner scanner = new Scanner(System.in);
        String resiAddress;
        boolean isValidInput;
        do {
            logger.info("Residential Address: ");
            resiAddress = scanner.nextLine();
            isValidInput = ValidationUtil.isValidResiAddress(resiAddress);
            if (resiAddress.isEmpty() || resiAddress.isBlank()) {
                isValidInput = false;
            } else if (isValidInput) {
                logger.warn("Acceptable between 10 and 200 characters");
            }
        } while (isValidInput);
        return resiAddress;
    }

    /**
     * <p>
     * This method will get the email ID from the user.
     * </p>
     *
     * (e.g): emailId = "some@email.com"
     *
     * @return String emailId;
     */
    private String getEmailId() {
        Scanner scanner = new Scanner(System.in);
        String emailId;
        boolean isValidInput;
        do {
            logger.info("Email ID*: ");
            emailId = scanner.nextLine().toLowerCase();
            isValidInput = ValidationUtil.isValidEmail(emailId);
            if (!isValidInput) {
                logger.warn("Invalid Email Format");
            }
        } while (!isValidInput);
        return emailId;
    }

    /**
     * <p>
     * This method will get the date of joining from the user.
     * </p>
     *
     * (e.g): dateOfJoining = 12-12-2020
     *
     * @param Date dateOfBirth
     * @return Date dateOfJoining
     */
    private Date getDateOfJoining(Date dateOfBirth) {
        Scanner scanner = new Scanner(System.in);
        String joinDate;
        boolean isValidInput;
        int joinYear = 0;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date dateOfJoining = null;
        int birthYear = dateOfBirth.getYear();
        do {
            logger.info("Date Of Joining (dd-MM-yyyy)*: ");
            joinDate = scanner.next();
            isValidInput = ValidationUtil.isValidDate(joinDate);
            if (!isValidInput) {
                logger.warn(Constants.INVALID_FORMAT_MSG);
                isValidInput = false;
            } else {
                joinYear = Integer.parseInt(joinDate.substring(6, 10));
                if ((birthYear + 18) < joinYear
                    && joinYear < currentYear + 1) {
                    isValidInput = ValidationUtil.isValidDate(joinDate);
                    if (!isValidInput) {
                        logger.warn(Constants.INVALID_FORMAT_MSG);
                        isValidInput = true;
                    } else {
                        try {
                            dateOfJoining =
                                new SimpleDateFormat(Constants.DATE_FORMAT).parse(joinDate);
                        } catch (ParseException parseException) {
                            logger.warn(Constants.INVALID_FORMAT_MSG, parseException.getMessage());
                        }
                    }
                } else {
                    logger.warn("Join year must be greater than " + (birthYear + 18) +
                                " and lesser than " + (currentYear + 1));
                    isValidInput = false;
                }
            }
        } while (!isValidInput);
        return dateOfJoining;
    }

    /**
     * <p>
     * This method will get the previous experience from the user.
     * </p>
     *
     * (e.g): previousExperience = 3
     *
     * @param Date dateOfBirth
     * @return int previousExperience
     */
    private int getPreviousExperience(Date dateOfBirth) {
        Scanner scanner = new Scanner(System.in);
        int previousExperience = 0;
        String previousExperienceString;
        boolean isValidInput;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = dateOfBirth.getYear();
        do {
            logger.info("Previous Experience: ");
            previousExperienceString = scanner.nextLine();
            if (previousExperienceString.isEmpty()
                || previousExperienceString.isBlank()) {
                isValidInput = false;
            } else {
                isValidInput = ValidationUtil.isValidNumber(previousExperienceString);
                if (isValidInput) {
                    previousExperience =
                            Integer.parseInt(previousExperienceString);
                    if (previousExperience < currentYear - (birthYear + 18) &&
                        previousExperience >= 0) {
                        isValidInput = true;
                    } else {
                        logger.warn("Experience can't be more than current year.");
                        isValidInput = false;
                    }
                } else {
                    logger.warn(Constants.INVALID_INPUT_MSG);
                }
            }
        } while (!isValidInput);
        return previousExperience;
    }

    /**
     * <p>
     * This method will get the trainer's name from the user.
     * </p>
     *
     * (e.g): trainersName = "Sherlock"
     *
     * @return String trainersName
     */
    private String getTrainersName() {
        Scanner scanner = new Scanner(System.in);
        String trainersName;
        boolean isValidInput;
        do {
            logger.info("Trainer Name: ");
            trainersName = scanner.nextLine();
            isValidInput = ValidationUtil.isValidString(trainersName);
            if (trainersName.isEmpty() || trainersName.isBlank()) {
                isValidInput = true;
            } else if (!isValidInput) {
                logger.warn("Trainer's Name must not contain numbers," +
                            "symbols or be empty");
            }
        } while (!isValidInput);
        return trainersName;
    }

    /**
     * <p>
     * This method will get the training course from the user.
     * </p>
     *
     * (e.g): trainingCourse = "java"
     *
     * @return String trainingCourse
     */
    private String getTrainingCourse() {
        Scanner scanner = new Scanner(System.in);
        String trainingCourse;
        boolean isValidInput;
        do {
            logger.info("Enter Training Course: ");
            trainingCourse = scanner.nextLine();
            isValidInput = ValidationUtil.isValidString(trainingCourse);
            if (trainingCourse.isEmpty() || trainingCourse.isBlank()) {
                isValidInput = true;
            } else if (!isValidInput) {
                logger.warn("Training Course must be a word.");
            }
        } while (!isValidInput);
        return trainingCourse;
    }

    /**
     * <p>
     * This method will get the traineeCount from the user.
     * </p>
     *
     * (e.g): traineeCount = 10
     *
     * @return int traineeCount
     */
    private int getTraineeCount() {
        Scanner scanner = new Scanner(System.in);
        int traineeCount = 0;
        String traineeCountString;
        boolean isValidInput;
        do {
            logger.info("Trainee Count: ");
            traineeCountString = scanner.nextLine();
            if (traineeCountString.isEmpty() || traineeCountString.isBlank()) {
                isValidInput = false;
            } else {
                isValidInput = ValidationUtil.isValidNumber(traineeCountString);
                if (isValidInput) {
                    traineeCount = Integer.parseInt(traineeCountString);
                    if (traineeCount > 25 || traineeCount < 0) {
                        logger.warn("Trainee Count must not exceed 25 members");
                        isValidInput = true;
                    } else {
                        isValidInput = false;
                    }
                } else {
                    logger.warn(Constants.INVALID_INPUT_MSG);
                    isValidInput = true;
                }
            }
        } while (isValidInput);
        return traineeCount;
    }

    /**
     * <p>
     * This method will get the training experience from the user.
     * </p>
     *
     * (e.g): trainingExperience = 3
     *
     * @param Date dateOfBirth
     * @return int trainingExperience
     */
    private int getTrainingExperience(Date dateOfBirth) {
        Scanner scanner = new Scanner(System.in);
        int trainingExperience = 0;
        String trainingExperienceString;
        boolean isValidInput;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = dateOfBirth.getYear();
        do {
            logger.info("Experience In Training (in years): ");
            trainingExperienceString = scanner.nextLine();
            if (trainingExperienceString.isEmpty() || trainingExperienceString.isBlank()) {
                isValidInput = false;
            } else {
                isValidInput = ValidationUtil.isValidNumber(trainingExperienceString);
                if (isValidInput) {
                    if (Integer.parseInt(trainingExperienceString) < currentYear - (birthYear + 18)) {
                        trainingExperience = Integer.parseInt(trainingExperienceString);
                        isValidInput = false;
                    } else {
                        logger.warn("Experience in Training can't be more than current year.");
                        isValidInput = true;
                    }
                } else {
                    logger.warn(Constants.INVALID_INPUT_MSG);
                    isValidInput = true;
                }
            }
        } while (isValidInput);
        return trainingExperience;
    }

    /**
     * <p>
     * This method will get the preferred training skill from the user.
     * </p>
     *
     * (e.g): prefTrainingSkill = java,python
     *
     * @return Set<String> prefTrainingSkill
     */
    private Set<String> getPrefTrainingSkill() {
        Scanner scanner = new Scanner(System.in);
        String trainingSkill;
        boolean isValidInput;
        Set<String> prefTrainingSkill = new HashSet<>();
        do {
            logger.info("Preferred Skill for Training (use commas to separate e.g: java,python ): ");
            trainingSkill = scanner.nextLine();
            isValidInput = ValidationUtil.isValidListString(trainingSkill);
            if (isValidInput) {
                prefTrainingSkill =
                        new HashSet<String>(Arrays.asList(trainingSkill.split(",")));
            } else {
                isValidInput = false;
                logger.warn("Only words without space accepted");
            }
        } while (!isValidInput);
        return prefTrainingSkill;
    }

    /**
     * <p>
     * This method will update the name if given by the user.
     * </p>
     *
     * (e.g): name = "Holmes"
     *
     * @return String name
     */
    private String getNameUpdate() {
        Scanner scanner = new Scanner(System.in);
        String name;
        boolean isValidInput;
        do {
            logger.info("Name: ");
            name = scanner.nextLine();
            isValidInput = ValidationUtil.isValidString(name);
            if (name.isEmpty()) {
                isValidInput = true;
            } else if (!isValidInput) {
                logger.warn("Name must not contain numbers, symbols.");
            }      
        } while (!isValidInput);
        return name;
    }
    
    /**
     * <p>
     * This method will update the gender if given by the user.
     * </p>
     *
     * (e.g): gender = 1 (male)
     *
     * @return String gender
     */
    private String getGenderUpdate() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        String optionString;
        String gender = "";
        boolean isValidInput;
        do {
            logger.info("Please Choose A Gender\n1. Male\n2. Female\n3. Others");
            optionString = scanner.nextLine();
            isValidInput = ValidationUtil.isValidNumber(optionString);
            if (optionString.isEmpty() || optionString.isBlank()) {
                isValidInput = false;
                option = 1;
            } else if (isValidInput) {
                switch (optionString) {
                    case "1":
                        gender = Gender.MALE.name();
                        option = Integer.parseInt(optionString);
                        break;
                    case "2":
                        gender = Gender.FEMALE.name();
                        option = Integer.parseInt(optionString);
                        break;
                    case "3":
                        gender = Gender.OTHERS.name();
                        option = Integer.parseInt(optionString);
                        break;
                    default:
                        logger.warn(Constants.INVALID_INPUT_MSG);
                        break;
                }
            } else {
                logger.warn(Constants.INVALID_INPUT_MSG);
            }
        } while (option < 1 || option > 3 && isValidInput);
        return gender;
    }

    /**
     * <p>
     * This method will update a trainee's date of birth if given by the user.
     * </p>
     *
     * (e.g): dateOfBirth = 10-08-1998
     *
     * @param int id
     * @return Date dateOfBirth
     */
    private Date getTraineeDateOfBirthUpdate(int id) {
        Scanner scanner = new Scanner(System.in);
        String birthDate;
        boolean isValidInput;
        Date dateOfBirth = null;
        int birthYear;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        do {    
            logger.info("Date Of Birth (dd-MM-yyyy): ");
            birthDate = scanner.nextLine();
            if (birthDate.isEmpty()) {
                isValidInput = true;
                dateOfBirth = employeeService.getTraineeDates(id).get(0);
                birthYear = Integer.parseInt(dateOfBirth.toString().substring(0, 4));
                logger.info(dateOfBirth + " " + birthYear);
            } else {
                isValidInput = ValidationUtil.isValidDate(birthDate);
                if (!isValidInput) {
                    logger.warn(Constants.INVALID_FORMAT_MSG);
                } else {
                    birthYear = Integer.parseInt(birthDate.substring(6, 10));
                    if (currentYear - birthYear >= Constants.MIN_AGE
                        && currentYear - birthYear <= Constants.MAX_AGE) {   
                        if (!isValidInput) {
                            logger.info(Constants.INVALID_FORMAT_MSG);
                        } else {
                            try {
                                dateOfBirth =
                                    new SimpleDateFormat(Constants.DATE_FORMAT).parse(birthDate);
                            } catch (ParseException parseException) {
                                logger.warn(Constants.INVALID_FORMAT_MSG, parseException.getMessage());
                            }
                        }
                    } else {
                        logger.warn("Age must be between 18 and 85");
                        isValidInput = false;
                    }
                }
            }
        } while (!isValidInput);
        return dateOfBirth;
    }

    /**
     * <p>
     * This method will update a trainer's date of birth if given by the user.
     * </p>
     *
     * (e.g): dateOfBirth = 13-08-1999
     *
     * @param int id
     * @return Date dateOfBirth
     */
    private Date getTrainerDateOfBirthUpdate(int id) {
        Scanner scanner = new Scanner(System.in);
        String birthDate;
        boolean isValidInput;
        Date dateOfBirth = null;
        int birthYear;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        do {
            logger.info("Date Of Birth (dd-MM-yyyy): ");
            birthDate = scanner.nextLine();
            if (birthDate.isEmpty()) {
                isValidInput = true;
                dateOfBirth =
                        employeeService.getTrainerDates(id).get(0);
                birthYear =
                        Integer.parseInt(dateOfBirth.toString().substring(0, 4));
            } else {
                isValidInput =
                        ValidationUtil.isValidDate(birthDate);
                if (!isValidInput) {
                    logger.warn(Constants.INVALID_FORMAT_MSG);
                } else {
                    birthYear =
                            Integer.parseInt(birthDate.substring(6, 10));
                    if (currentYear - birthYear >= Constants.MIN_AGE
                        && currentYear - birthYear <= Constants.MAX_AGE) {   
                        if (!isValidInput) {
                            logger.info(Constants.INVALID_FORMAT_MSG);
                            birthDate = null;
                        } else {
                            try {
                                dateOfBirth =
                                    new SimpleDateFormat(Constants.DATE_FORMAT).parse(birthDate);
                            } catch (ParseException parseException) {
                                logger.warn(Constants.INVALID_FORMAT_MSG, parseException.getMessage());
                            }
                        }
                    } else {
                        logger.warn("Age must be between 18 and 85");
                        birthDate = null;
                        isValidInput = false;
                    }
                }
            }
        } while (!isValidInput);
        return dateOfBirth;
    }

    /**
     * <p>
     * This method will update the residential address if given by the user.
     * </p>
     *
     * (e.g): resiAddress = "some address that goes here"
     *
     * @return String resiAddress;
     */
    private String getResiAddressUpdate() {
        Scanner scanner = new Scanner(System.in);
        String resiAddress;
        boolean isValidInput;
        do {
            logger.info("Residential Address: ");
            resiAddress = scanner.nextLine();
            isValidInput = ValidationUtil.isValidResiAddress(resiAddress);
            if (resiAddress.isEmpty()) {
                isValidInput = false;
            } else if (isValidInput) {
                logger.warn("Acceptable between 10 and 200 characters");
            }
        } while (isValidInput);
        return resiAddress;
    }

    /**
     * <p>
     * This method will update the email ID if given by the user.
     * </p>
     *
     * (e.g): emailId = "update@email.com"
     *
     * @return String emailId
     */
    private String getEmailIdUpdate() {
        Scanner scanner = new Scanner(System.in);
        String emailId;
        boolean isValidInput;
        do {
            logger.info("Email ID: ");
            emailId = scanner.nextLine().toLowerCase();
            isValidInput = ValidationUtil.isValidEmail(emailId);
            if (emailId.isEmpty()) {
                isValidInput = true;
            } else if (!isValidInput) {
                logger.warn("Invalid Email Format");
            }
        } while (!isValidInput);
        return emailId;
    }

    /**
     * <p>
     * This method will update a trainee's date of joining if given by the user.
     * </p>
     *
     * (e.g): dateOfJoining = 12-12-2020
     *
     * @param Date dateOfBirth
     * @param int id
     * @return Date dateOfJoining
     */
    private Date getTraineeDateOfJoiningUpdate(Date dateOfBirth, int id) {
        Scanner scanner = new Scanner(System.in);
        String joinDate;
        boolean isValidInput;
        Date dateOfJoining = null;
        int birthYear = dateOfBirth.getYear();
        int joinYear = 0;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        do {
            logger.info("Date Of Joining (dd-MM-yyyy): ");
            joinDate = scanner.nextLine();
            if (joinDate.isEmpty()) {
                isValidInput = true;
                dateOfJoining =
                        employeeService.getTraineeDates(id).get(1);
            } else {
                isValidInput =
                        ValidationUtil.isValidDate(joinDate);
                if (!isValidInput) {
                    logger.warn(Constants.INVALID_FORMAT_MSG);
                } else {
                    joinYear =
                        Integer.parseInt(joinDate.substring(6, 10));
                    if ((birthYear + 18) < joinYear
                        && joinYear < currentYear + 1) {
                        isValidInput =
                                ValidationUtil.isValidDate(joinDate);
                        if (!isValidInput) {
                            logger.warn(Constants.INVALID_FORMAT_MSG);
                        } else {
                            try {
                                dateOfJoining =
                                    new SimpleDateFormat(Constants.DATE_FORMAT).parse(joinDate);
                            } catch (ParseException parseException) {
                                logger.warn(Constants.INVALID_FORMAT_MSG, parseException.getMessage());
                            }
                        }
                    } else {
                        logger.warn("Join year must be greater than " + (birthYear + 18) +
                                    " and lesser than " + (currentYear + 1));
                        isValidInput = false;
                    }
                } 
            }
        } while (!isValidInput); 
        return dateOfJoining;
    }

    /**
     * <p>
     * This method will update a trainer's date of joining if given by the user.
     * </p>
     *
     * (e.g): dateOfJoining = 12-12-2020
     *
     * @param Date dateOfBirth
     * @param int id
     * @return Date dateOfJoining
     */
    private Date getTrainerDateOfJoiningUpdate(Date dateOfBirth, int id) {
        Scanner scanner = new Scanner(System.in);
        String joinDate;
        boolean isValidInput;
        Date dateOfJoining = null;
        int joinYear;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = dateOfBirth.getYear();
        do {
            logger.info("Date Of Joining (dd-MM-yyyy): ");
            joinDate = scanner.nextLine();
            if (joinDate.isEmpty()) {
                isValidInput = true;
                dateOfJoining =
                        employeeService.getTrainerDates(id).get(1);
                logger.info(dateOfJoining);
            } else {
                isValidInput =
                        ValidationUtil.isValidDate(joinDate);
                if (!isValidInput) {
                    logger.warn(Constants.INVALID_FORMAT_MSG);
                } else {
                    joinYear =
                        Integer.parseInt(joinDate.substring(6, 10));
                    if ((birthYear + 18) < joinYear
                        && joinYear < currentYear + 1) {
                        isValidInput =
                                ValidationUtil.isValidDate(joinDate);
                        if (!isValidInput) {
                            logger.warn(Constants.INVALID_FORMAT_MSG);
                            joinDate = null;
                        } else {
                            try {
                                dateOfJoining =
                                        new SimpleDateFormat(Constants.DATE_FORMAT).parse(joinDate);
                            } catch (ParseException parseException) {
                                logger.error(parseException.getMessage());
                            }
                        }
                    } else {
                        logger.warn("Join year must be greater than " + (birthYear + 18)
                            + " and lesser than " + (currentYear + 1));
                        isValidInput = false;
                        joinDate = null;
                    }
                }
            }
        } while (!isValidInput); 
        return dateOfJoining;
    }

    /**
     * <p>
     * This method will update the previous experience if given by the user.
     * </p>
     *
     * (e.g): previousExperience = 3
     *
     * @param Date dateOfBirth
     * @return int previousExperience
     */
    private int getPreviousExperienceUpdate(Date dateOfBirth) {
        Scanner scanner = new Scanner(System.in);
        int previousExperience = 0;
        String previousExperienceString;
        boolean isValidInput = false;
        int birthYear = dateOfBirth.getYear();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        do {
            logger.info("Enter Previous Experience: ");
            previousExperienceString = scanner.nextLine();
            isValidInput =
                    ValidationUtil.isValidNumber(previousExperienceString);
            if (previousExperienceString.isEmpty()) {
                isValidInput = true;
            } else if (isValidInput) {
                previousExperience =
                        Integer.parseInt(previousExperienceString);
                if (previousExperience < currentYear - (birthYear + 18)
                    && previousExperience >= 0) {
                    isValidInput = true;
                } else {
                    logger.warn("Experience can't be more than current year.");
                    isValidInput = false;
                }
            } else {
                logger.warn("Previous Experience Must Be A Number");
            }
        } while (!isValidInput);
        return previousExperience;
    }

    /**
     * <p>
     * This method will update the trainer's name if given by the user.
     * </p>
     *
     * (e.g): trainersName = "Sherlock"
     *
     * @return String trainersName
     */
    private String getTrainersNameUpdate() {
        Scanner scanner = new Scanner(System.in);
        String trainersName;
        boolean isValidInput;
        do {
            logger.info("Enter Trainer's Name: ");
            trainersName = scanner.nextLine();
            isValidInput =
                    ValidationUtil.isValidString(trainersName);
            if (trainersName.isEmpty()) {
                isValidInput = true;
            } else if (!isValidInput) {
                isValidInput = false;
                logger.warn("Name must not contain numbers, symbols.");
            }      
        } while (!isValidInput);
        return trainersName;
    }

    /**
     * <p>
     * This method will update the training course if given by the user.
     * </p>
     *
     * (e.g): trainingCourse = "Java"
     *
     * @return String trainingCourse
     */
    private String getTrainingCourseUpdate() {
        Scanner scanner = new Scanner(System.in);
        String trainingCourse;
        boolean isValidInput;
        do {
            logger.info("Enter Training Course: ");
            trainingCourse = scanner.nextLine();
            isValidInput =
                    ValidationUtil.isValidString(trainingCourse);
            if (trainingCourse.isEmpty()) {
                isValidInput = true;
            } else if (!isValidInput) {
                isValidInput = false;
                logger.warn("Training Course must be a word.");
            }
        } while (!isValidInput);
        return trainingCourse;
    }

    /**
     * <p>
     * This method will update the trainee count if given by the user.
     * </p>
     *
     * (e.g): traineeCount = 10
     *
     * @return int traineeCount
     */
    private int getTraineeCountUpdate() {
        Scanner scanner = new Scanner(System.in);
        String traineeCountString;
        int traineeCount = 0;
        boolean isValidInput = false;
        do {
            logger.info("Enter Trainee Count: ");
            traineeCountString = scanner.nextLine();
            isValidInput =
                    ValidationUtil.isValidNumber(traineeCountString);
            if (traineeCountString.isEmpty()) {
                isValidInput = true;
            } else if (isValidInput) {
                traineeCount =
                        Integer.parseInt(traineeCountString);
                if (traineeCount > 25 || traineeCount < 0) {
                    logger.warn("Trainee Count must not exceed 25 members");
                    isValidInput = false;
                }
            } else {
                logger.warn("Previous Experience Must Be A Number");
            }
        } while (!isValidInput);
        return traineeCount;
    }

    /**
     * <p>
     * This method will update the training experience if given by the user.
     * </p>
     *
     * (e.g): trainingExperience = 3
     *
     * @param Date dateOfBirth
     * @return int trainingExperience
     */
    private int getTrainingExperienceUpdate(Date dateOfBirth) {
        Scanner scanner = new Scanner(System.in);
        String trainingExperienceString;
        int trainingExperience = 0;
        boolean isValidInput;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthYear = dateOfBirth.getYear();
        do {
            logger.info("Enter Years Of Experience In Training: ");
            trainingExperienceString = scanner.nextLine();
            isValidInput =
                    ValidationUtil.isValidNumber(trainingExperienceString);
            if (trainingExperienceString.isEmpty()) {
                isValidInput = true;
            } else if (!isValidInput) {
                logger.warn("Previous Experience Must Be A Number");
            } else {
                isValidInput = true;
                trainingExperience =
                        Integer.parseInt(trainingExperienceString);
                if (trainingExperience < currentYear - (birthYear + 18)) {
                    isValidInput = true;
                } else {
                    logger.warn("Experience in Training can't be more than current year.");
                    isValidInput = false;
                }
            }
        } while (!isValidInput);
        return trainingExperience;
    }

    /**
     * <p>
     * This method will update the preferred training skill if given by the user.
     * </p>
     *
     * (e.g): prefTrainingSkill = python,java
     *
     * @param int id
     * @return Set<String> prefTrainingSkill
     */
    private Set<String> getPrefTrainingSkillUpdate(int id) {
        Scanner scanner = new Scanner(System.in);
        String trainingSkill;
        boolean isValidInput;
        Set<String> prefTrainingSkill = new HashSet<>();
        try {
            do {
                logger.info("Preferred Skill for Training (use commas to separate e.g: java,python ): ");
                trainingSkill = scanner.nextLine();
                isValidInput =
                        ValidationUtil.isValidListString(trainingSkill);
                if (trainingSkill.isEmpty()) {
                    prefTrainingSkill =
                            EmployeeMapper.entityToDto(employeeService.searchTrainerById(id)).getPrefTrainingSkill();
                    isValidInput = true;
                } else if (!isValidInput) {
                    logger.warn("Only words without space accepted");
                } else {
                    isValidInput = true;
                    prefTrainingSkill =
                            new HashSet<String>(Arrays.asList(trainingSkill.split(",")));
                }
            } while (!isValidInput);
        } catch (EmployeeException employeeException) {
            logger.error(Constants.EMPLOYEE_EXCEPTION, employeeException.getMessage());
        }
        return prefTrainingSkill;
    }

    /**
     * <p>
     * This method will get yes or no from the user.
     * </p>
     *
     * (e.g): yesOrNo = "y"
     *
     * @return String yesOrNo
     */
    private String getYesOrNo() {
        Scanner scanner = new Scanner(System.in);
        String yesOrNo;
        boolean isValidInput;
        do {
            logger.info("Submit the details? (y/n): ");
            yesOrNo = scanner.next().toLowerCase();
            isValidInput = ValidationUtil.isValidYesOrNo(yesOrNo);
            if (!isValidInput) {
                logger.warn("Values must be y/n");
            }
        } while (!isValidInput);
        return yesOrNo;
    }
}