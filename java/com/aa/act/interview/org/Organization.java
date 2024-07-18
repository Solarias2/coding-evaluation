package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

    private Position root;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        //Modified to call a recursive function.
        return hireRecursive(root, person, title);
    }

    /*
     * Recursive function to tranverse through the tree in order 
     * to find the available position and fill it out based off the
     * attributes from the original hire method.
     */
    private Optional<Position> hireRecursive(Position current, Name person, String title) {
        /*
         * If the title exists in the Position class, it would 
         * assign the role to the person associated in the tree.
         */
        if (current.getTitle().equals(title)) {
            current.setEmployee(Optional.of(new Employee(generateIdentifier(), person)));
            return Optional.of(current);
        }

        /*
         * Once assigned, the for loop runs and tranverses down 
         * the tree. When the employee is hired from 
         * the previous code, it would appear in the for loop which would
         * fulfill the if condition, stopping the recursion and overall method,
         * saving the instance. 
         */
        for (Position directReport : current.getDirectReports()) {
            Optional<Position> hiredPosition = hireRecursive(directReport, person, title);
            if (hiredPosition.isPresent()) {
                return hiredPosition;
            }
        }
        //Returns if nothing is assigned or 
        return Optional.empty();
    }

    /*
     * Method to randomly select a number to assign an identifier for the employee.
     * Simple as it is only a coding interview and not apart of a main architecture.
     */
    private int generateIdentifier() {
        return (int) (Math.random() * 10000);
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
