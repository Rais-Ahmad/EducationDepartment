package com.example.EducationDepartment.oAuth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("api").stateless(false);
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().authorizeRequests()

				.antMatchers("/user/allUsers", "/permission/allPermissions", "/institution/allInstitutions",
						"/exam/allExam", "/user/allVerifiedUsers'List", "/degree/allDegree",
						"/curriculum/allCurriculum")
				.hasAnyAuthority("admin", "superAdmin")
				.antMatchers("/user/userPersonalDetails", "/user/updateUser'sPhone", "/user/updateUser'sPassword",
						"/user/updateUser'sName", "/user/sendToken/{id}", "/user/Verification",
						"/department/allDepartments")
				.hasAnyAuthority("admin", "superAdmin", "student", "teacher")

				.antMatchers("​/user​/updateStudent'sDegreeById", "/user/qualificationVerification",
						"/user/cnicVerification", "/exam/updateExam", "/exam/addExam", "/department/addDepartment",
						"/institution/addInstitution", "/institution/updateInstitute", "/department/updateDepartment",
						"/degree/addDegree", "/degree/updateDegree", "/curriculum/addCurriculum",
						"​/curriculum​/updateCurriculum")
				.hasAuthority("admin")

				.antMatchers("/user/getResultByStudent", "/user/degreeVerification").hasAuthority("student")

				.antMatchers("/user/allUsersListByClass", "/result/allResults", "​/result​/addResult",
						"/result/updateResult")
				.hasAuthority("teacher")

				.antMatchers("​/user​/updateAllUserInformation", "/permission/addPermission",
						"/permission/updatePermission", "/role/**")
				.hasAuthority("superAdmin")

				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}