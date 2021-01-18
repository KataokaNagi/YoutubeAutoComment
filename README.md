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
- Update `API_KEY` on `src/main/java/com/github/kataokanagi/youtubeapi/Config.java`
- Update `VIDEO_ID` on `src/main/java/com/github/kataokanagi/YoutubeAutoComment.java`
- Update `client_id` and `client_secret` on `src/main/resources/client_secrets.json`
- Run `src/main/java/com/github/kataokanagi/YoutubeAutoComment.java`
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

2021/01/19 updated  
![image](https://user-images.githubusercontent.com/45355440/104934185-2ce10e00-59ed-11eb-8061-31e19b8dd615.png)

### Class Diagram

2021/01/19 updated  
![ClassDiagram](https://user-images.githubusercontent.com/45355440/104935019-125b6480-59ee-11eb-9ac4-9557ffb3629c.png)

### Scequence Diagram (Get Comment)

2021/01/19 updated  
![SequenceDiagram_GetComment](https://user-images.githubusercontent.com/45355440/104935106-2bfcac00-59ee-11eb-8c96-e5f800ca4b18.png)

### Scequence Diagram (Analyse & Send Comment)

2021/01/19 updated  
![SequenceDiagram_CommentAnalyser](https://user-images.githubusercontent.com/45355440/104939264-8fd5a380-59f3-11eb-9ae0-e76bc652966c.png)
