import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetRoleDetailsComponent } from 'app/entities/dataset-role-details/dataset-role-details.component';
import { DatasetRoleDetailsService } from 'app/entities/dataset-role-details/dataset-role-details.service';
import { DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

describe('Component Tests', () => {
  describe('DatasetRoleDetails Management Component', () => {
    let comp: DatasetRoleDetailsComponent;
    let fixture: ComponentFixture<DatasetRoleDetailsComponent>;
    let service: DatasetRoleDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetRoleDetailsComponent],
        providers: [],
      })
        .overrideTemplate(DatasetRoleDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetRoleDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetRoleDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetRoleDetails(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetRoleDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
