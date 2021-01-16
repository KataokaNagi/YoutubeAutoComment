# YoutubeAutoComment

※ Under development on the `refactor` branch

## Overview

This system provide the following utility of Youtube

- Checking all comments specified video repeatedly using Youtube Data API
- Genarating replys according to the emotion level of japanese comments using morphological analysis and polar dictionaly
- Insert reply into the video using Youtube Data API
  2021/01/07 updated

## Usage

- Get your API key on [Google Developer Console](https://console.developers.google.com/?hl=JA)
- Check your client_id and client_secret corresponded to your API key on [Google Developer Console](https://console.developers.google.com/?hl=JA)
- Check video id (After "watch?v=" of the video URL)
- Update `API_KEY` on `src/main/java/com/github/kataokanagi/YtubeAutoCmntMain.java`
- Update `VIDEO_ID` on `src/main/java/com/github/kataokanagi/YtubeAutoCmntMain.java`
- Update `client_id` and `client_secret` on `src/main/resources/client_secrets.json`
- Run `src/main/java/com/github/kataokanagi/YtubeAutoCmntMain.java`
- Permit the rights of Youtube on your browser
  2021/01/07 updated

## Youtube Link

See the comment field.
http://www.youtube.com/watch?v=F6Z8OWJD5IE

[![](http://img.youtube.com/vi/F6Z8OWJD5IE/0.jpg)](http://www.youtube.com/watch?v=F6Z8OWJD5IE "youtube_link")

## Design

### Usecase Diagram

2021/01/07 updated  
![image](https://user-images.githubusercontent.com/45355440/103889571-fcb28900-5129-11eb-80af-3add753f908d.png)

### Scenario

2021/01/07 updated  
![image](https://user-images.githubusercontent.com/45355440/103889782-531fc780-512a-11eb-9e64-9e329a4e8720.png)

### Class Diagram

2021/01/07 updated  
![ClassDiagram](https://user-images.githubusercontent.com/45355440/103890142-e22cdf80-512a-11eb-9206-a7822dbd674e.png)

### Scequence Diagram (Get Comment)

2021/01/07 updated  
![SequenceDiagram_GetComment](https://user-images.githubusercontent.com/45355440/103890301-29b36b80-512b-11eb-8865-9d7d39c10c81.png)

### Scequence Diagram (Analyse & Send Comment)

2021/01/07 updated  
![SequenceDiagram_CommentAnalyser](https://user-images.githubusercontent.com/45355440/103890357-4780d080-512b-11eb-9c2a-d51f007e8294.png)
