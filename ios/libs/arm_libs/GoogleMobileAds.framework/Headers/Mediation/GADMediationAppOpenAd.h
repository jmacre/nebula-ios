//
//  GADMediationAppOpenAd.h
//  Google Mobile Ads SDK
//
//  Copyright 2022 Google LLC. All rights reserved.
//

#import "GADMediationAd.h"
#import "GADMediationAdConfiguration.h"
#import "GADMediationAdEventDelegate.h"
#import <UIKit/UIKit.h>

/// Rendered app open ad.
@protocol GADMediationAppOpenAd <GADMediationAd>

/// Presents the receiver from the view controller.
- (void)presentFromViewController:(nonnull UIViewController *)viewController;
@end

/// App open ad configuration.
@interface GADMediationAppOpenAdConfiguration : GADMediationAdConfiguration
@end
