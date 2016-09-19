<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
 
<body> 

<h2>Multipart file Uploading  </h2>
	<form:form method="POST" commandName="file"	enctype="multipart/form-data">
 
		<p>you can choose multiple files at time </p>
		choose files :<input type="file" name="files" multiple>
		<input type="submit" value="upload" />
		<form:errors path="files" cssStyle="color: #ff0000;" />
	</form:form>
 
</body>
</html>