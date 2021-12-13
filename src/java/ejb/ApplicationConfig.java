/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 2dam
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ejb.CourseFacadeREST.class);
        resources.add(ejb.ExamFacadeREST.class);
        resources.add(ejb.ExamSessionFacadeREST.class);
        resources.add(ejb.StudentFacadeREST.class);
        resources.add(ejb.SubjectFacadeREST.class);
        resources.add(ejb.TeacherCourseFacadeREST.class);
        resources.add(ejb.TeacherFacadeREST.class);
        resources.add(ejb.UserFacadeREST.class);
    }
    
}
