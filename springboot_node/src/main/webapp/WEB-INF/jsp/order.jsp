<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<c:import url="head.jsp" />

</head>

<body>
    <c:import url="navigation.jsp" />
    
      <main>
      
      <c:choose>
  		<c:when test="${empty orders}">
  		
  		<h1>Nothing there!</h1>
  		
    
  		</c:when>
 
  	<c:otherwise>
  	
  		
  			<ul>
  			
  			<c:forEach var="order" items="${orders}">
  			
  					<li>
                            <h1># ${order.id}</h1>
                            <ul>
                                <c:forEach var="orderitem" items="${order.orderitems}">
                                    <li>${orderitem.product.title} (${orderitem.qty})</li>
                                </c:forEach>
                            </ul>
                        </li>
  			
  			
  		 	</c:forEach>
  		 	
  		 	</ul>
             
  
  	</c:otherwise>
	
	</c:choose>
       
           
      </main>
      
      <c:import url="end.jsp" />