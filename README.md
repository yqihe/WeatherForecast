# WeatherForecast
### by zhengqizhuang @yqihe 2024/10/10/01:13
## 日历系统UI的改进

**界面设计**：您采纳了我关于模仿 Apple 日历清晰简洁风格的建议，重新设计了界面的布局和交互逻辑。现在用户可以更直观地浏览日程安排，简化了操作步骤，让界面更符合用户习惯。

**事件管理功能**：我改进了事件创建和编辑功能，简化了输入流程。同时，我也增加了多日程同步管理功能，用户现在可以轻松导入和管理来自其他平台的日程。

**通知和提醒**：我在提醒系统中增加了更多的灵活选项，同时也开始研究如何根据用户的日常活动进行智能提醒的推荐。虽然这一功能还在开发中，但我认为它将极大提升项目的实用性。

首先，我想展示一下之前的日历UI。



![img](https://i.ibb.co/b16Hmwn/image.png)

我对他进行了修改，并添加了更多的展示功能和AI交互功能：

![img](https://i.ibb.co/x3wj3Sh/PRO.png)

## 日历系统智能化



我对日历系统进行了智能化优化，基于通义千问大模型，用户可以根据当天的日历，对千问日历助手进行提问



![img](https://i.ibb.co/3hkGkgp/Chat.png)



## 天气系统智能化

![img](https://i.ibb.co/1vRsrcR/image.png)

我修改的天气UI中加入了智能体按钮，如下图所示：

![img](https://i.ibb.co/CPPGhwR/PRO.png)

提问界面如下，其中，可以对天气和出行建议作答：



![img](https://i.ibb.co/whypy60/Chat.png)





主要的原理代码如下：

```java
import com.alibaba.dashscope.app.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import java.util.List;


public class Main{
    
    public static void callAgentApp()
        throws ApiException, NoApiKeyException, InputRequiredException {
        ApplicationParam param = ApplicationParam.builder()
            .apiKey("MY_API_KEY")
                .appId("MY_APP_ID")
                .prompt("根据今天天气的信息做一些建议")
                .build();

        Application application = new Application();
        ApplicationResult result = application.call(param);

        System.out.printf("requestId: %s, text: %s, finishReason: %s\n",
                result.getRequestId(), result.getOutput().getText(), result.getOutput().getFinishReason());
    }

    public static void main(String[] args) {
        try {
            callAgentApp();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.printf("Exception: %s", e.getMessage());
        }
        System.exit(0);
    }  
} 
```
