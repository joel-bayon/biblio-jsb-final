
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<div class="navbar-wrapper">
    <div class="container-fluid">
        <nav class="navbar navbar-fixed-top">
            <div class="container">
                
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li>Connexion de : <b>${nom}</b></li> 
                    </ul>
                    
                    <ul class="nav navbar-nav pull-right">
                    		<li>le : <b><fmt:formatDate value="${dateConnexion}" pattern="dd/MM/yyyy HH:mm"/></b></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>
