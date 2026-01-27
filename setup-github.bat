@echo off
REM Setup GitHub Repository for Insurance Claim Processing SOA

echo ================================================
echo Setting up GitHub Repository
echo ================================================
echo.

cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"

echo [Step 1] Removing parent git reference...
REM This folder is currently tracked by parent repo, we'll make it independent

echo [Step 2] Initialize new git repository...
git init

echo [Step 3] Add all files...
git add .

echo [Step 4] Create initial commit...
git commit -m "Initial commit: Insurance Claim Processing SOA

- REST API (Jersey)
- SOAP Service (JAX-WS)
- gRPC Service (Protocol Buffers)
- GraphQL API (graphql-java)
- Java Orchestrator
- Complete Documentation

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"

echo.
echo ================================================
echo Local repository initialized!
echo ================================================
echo.
echo Next steps:
echo 1. Create a new repository on GitHub:
echo    - Go to https://github.com/new
echo    - Name: Insurance-Claim-Processing-SOA
echo    - Private repository (recommended for team project)
echo.
echo 2. Copy the remote URL and run:
echo    git remote add origin https://github.com/YOUR_USERNAME/Insurance-Claim-Processing-SOA.git
echo    git branch -M main
echo    git push -u origin main
echo.
echo 3. Invite your teammate:
echo    - Go to Settings ^> Collaborators
echo    - Add: thijmen-joris.welberg@telecom-sudparis.eu
echo.

pause
